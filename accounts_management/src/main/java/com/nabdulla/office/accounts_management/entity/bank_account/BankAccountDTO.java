package com.nabdulla.office.accounts_management.entity.bank_account;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Relation(collectionRelation = "accounts", itemRelation = "account")
public class BankAccountDTO extends RepresentationModel<BankAccountDTO> {
    private String accountName;
    private double amount;

    public BankAccountDTO(BankAccount bankAccount){
        accountName = bankAccount.getAccountName();
        amount = bankAccount.getAmount();
    }
}
