package com.nabdulla.office.accounts_management.controller;

import com.nabdulla.office.accounts_management.entity.bank_account.*;
import com.nabdulla.office.accounts_management.repos.BankAccountRepo;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/accounts")
public class BankAccountResource {
    private final BankAccountRepo accountRepo;

    public BankAccountResource(BankAccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    @GetMapping(produces = "application/json")
    public CollectionModel<BankAccountDTO> getAccounts() {
        var accounts = accountRepo.findAll();
        CollectionModel<BankAccountDTO> models = new BankAccountDTOAssemblerSupport().toCollectionModel(accounts);
        models.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BankAccountResource.class).getAccounts()).withSelfRel());
        return models;
    }

    @GetMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<BankAccountDTO> getAccounts(@PathVariable("id") Long id) {
        var account = accountRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Bank Account Not Found"));
        return ResponseEntity.ok(new BankAccountDTOAssemblerSupport().toModel(account));
    }

    @GetMapping(path = "{id}/transactions", produces = "application/json")
    public CollectionModel<AccountTransactionDTO> getTransactions(@PathVariable("id") Long id) {
        var account = accountRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Bank Account Not Found"));
        var trxs = account.getTransactions();
        var models = new AccountTransactionDTOAssemblerSupport().toCollectionModel(trxs);
        models.add(linkTo(methodOn(BankAccountResource.class).getTransactions(account.getId())).withSelfRel());
        return models;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BankAccountDTO> createAccount(@Valid @RequestBody BankAccount bankAccount) {
        if (accountRepo.findBankAccountByAccountNameIgnoreCase(bankAccount.getAccountName()).isPresent())
            throw new EntityExistsException("The Bank Account Name Already Exists, Try another Name");

        var newAccount = accountRepo.save(bankAccount);
        return ResponseEntity.ok(new BankAccountDTOAssemblerSupport().toModel(newAccount));
    }
}
