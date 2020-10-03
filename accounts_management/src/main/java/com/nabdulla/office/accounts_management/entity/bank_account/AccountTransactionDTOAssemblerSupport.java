package com.nabdulla.office.accounts_management.entity.bank_account;

import com.nabdulla.office.accounts_management.controller.BankAccountResource;
import com.nabdulla.office.accounts_management.controller.BankTransactionResource;
import com.nabdulla.office.accounts_management.entity.bank_transaction.BankTransaction;
import lombok.SneakyThrows;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class AccountTransactionDTOAssemblerSupport extends RepresentationModelAssemblerSupport<BankTransaction, AccountTransactionDTO> {
    public AccountTransactionDTOAssemblerSupport() {
        super(BankAccountResource.class, AccountTransactionDTO.class);
    }

    @SneakyThrows
    @Override
    public AccountTransactionDTO toModel(BankTransaction entity) {
        var model = new AccountTransactionDTO(entity);
        model.add(linkTo(methodOn(BankTransactionResource.class).getTransaction(entity.getId())).withSelfRel());
        return model;
    }
}
