package com.nabdulla.office.accounts_management.entity.bank_transaction;

import com.nabdulla.office.accounts_management.controller.BankTransactionResource;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class UserAccountTransactionDTOAssemblerSupport extends RepresentationModelAssemblerSupport<BankTransaction, UserAccountTransactionDTO> {
    public UserAccountTransactionDTOAssemblerSupport() {
        super(BankTransactionResource.class, UserAccountTransactionDTO.class);
    }

    @Override
    public UserAccountTransactionDTO toModel(BankTransaction entity) {
        var dto = new UserAccountTransactionDTO(entity);
        dto.add(linkTo(methodOn(BankTransactionResource.class).getTransaction(entity.getId())).withSelfRel());
        return dto;
    }
}
