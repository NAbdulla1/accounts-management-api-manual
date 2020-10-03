package com.nabdulla.office.accounts_management.entity.user;

import com.nabdulla.office.accounts_management.controller.BankTransactionResource;
import com.nabdulla.office.accounts_management.entity.bank_transaction.BankTransaction;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class UserTransactionDTOAssemblerSupport extends RepresentationModelAssemblerSupport<BankTransaction, UserTransactionDTO> {

    public UserTransactionDTOAssemblerSupport() {
        super(BankTransactionResource.class, UserTransactionDTO.class);
    }

    @Override
    public UserTransactionDTO toModel(BankTransaction entity) {
        var dto = new UserTransactionDTO(entity);
        dto.add(linkTo(methodOn(BankTransactionResource.class).getTransaction(entity.getId())).withSelfRel());
        return dto;
    }
}
