package com.nabdulla.office.accounts_management.controller;

import com.nabdulla.office.accounts_management.entity.bank_transaction.*;
import com.nabdulla.office.accounts_management.security.User;
import com.nabdulla.office.accounts_management.repos.BankAccountRepo;
import com.nabdulla.office.accounts_management.repos.BankTransactionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/transactions")
@RequiredArgsConstructor
public class BankTransactionResource {
    private final BankTransactionRepo transactionRepo;
    private final BankAccountRepo accountRepo;

    @GetMapping(produces = "application/json")
    public ResponseEntity<CollectionModel<BankTransactionDTO>> getTransaction() {
        var trxs = transactionRepo.findAll();
        CollectionModel<BankTransactionDTO> models
                = new BankTransactionDTOAssemblerSupport().toCollectionModel(trxs);
        models.add(linkTo(methodOn(BankTransactionResource.class).getTransaction()).withSelfRel());
        return ResponseEntity.ok(models);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<BankTransactionDTO> getTransaction(@PathVariable("id") Long transactionId) {
        var trx = transactionRepo.findById(transactionId).orElseThrow(() -> new EntityNotFoundException("Transaction Not Found"));
        return ResponseEntity.ok(new BankTransactionDTOAssemblerSupport().toModel(trx));
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<BankTransactionDTO> makeTransaction(@Valid @RequestBody BankTransaction transaction, @AuthenticationPrincipal User user) {
        transaction.setAccount(accountRepo.findById(transaction.getAccount().getId()).orElseThrow(() -> new EntityNotFoundException("Bank Account Not Found")));
        var newTrx = transactionRepo.save(transaction);
        newTrx.setUser(user);
        var account = newTrx.getAccount();
        account.performTransaction(newTrx);
      /*  try {
            account.performTransaction(newTrx);
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw ex;
        }*/
        return ResponseEntity.ok(new BankTransactionDTOAssemblerSupport().toModel(newTrx));
    }

    @GetMapping(path = "/{username}/{accountId}", produces = "application/json")
    public ResponseEntity<CollectionModel<UserAccountTransactionDTO>> getUserAccountTransactions(@PathVariable("username") String username, @PathVariable("accountId") Long accountId) {
        var trxs = transactionRepo.findBankTransactionByUser_UsernameAndAccount_Id(username, accountId);
        CollectionModel<UserAccountTransactionDTO> models
                = new UserAccountTransactionDTOAssemblerSupport().toCollectionModel(trxs);
        models.add(linkTo(methodOn(BankTransactionResource.class).getTransaction()).withSelfRel());
        return ResponseEntity.ok(models);
    }

    //todo add more end points
}
