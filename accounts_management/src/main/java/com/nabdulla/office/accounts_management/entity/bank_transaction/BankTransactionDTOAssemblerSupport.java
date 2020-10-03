package com.nabdulla.office.accounts_management.entity.bank_transaction;

import com.nabdulla.office.accounts_management.controller.BankTransactionResource;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public class BankTransactionDTOAssemblerSupport extends RepresentationModelAssemblerSupport<BankTransaction, BankTransactionDTO> {

    public BankTransactionDTOAssemblerSupport() {
        super(BankTransactionResource.class, BankTransactionDTO.class);
    }

    @Override
    public BankTransactionDTO toModel(BankTransaction entity) {
        var model = new BankTransactionDTO(entity);
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BankTransactionResource.class).getTransaction(entity.getId())).withSelfRel());
        return model;
    }
}
