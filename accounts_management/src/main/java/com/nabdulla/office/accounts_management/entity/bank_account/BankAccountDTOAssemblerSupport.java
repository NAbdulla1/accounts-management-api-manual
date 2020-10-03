package com.nabdulla.office.accounts_management.entity.bank_account;

import com.nabdulla.office.accounts_management.controller.BankAccountResource;
import lombok.SneakyThrows;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class BankAccountDTOAssemblerSupport extends RepresentationModelAssemblerSupport<BankAccount, BankAccountDTO> {
    public BankAccountDTOAssemblerSupport() {
        super(BankAccountResource.class, BankAccountDTO.class);
    }

    @SneakyThrows
    @Override
    public BankAccountDTO toModel(BankAccount entity) {
        var model = new BankAccountDTO(entity);
        model.add(linkTo(methodOn(BankAccountResource.class).getAccounts(entity.getId())).withSelfRel());
        return model;
    }
}
