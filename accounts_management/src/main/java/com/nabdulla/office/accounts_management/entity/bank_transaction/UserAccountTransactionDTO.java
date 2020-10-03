package com.nabdulla.office.accounts_management.entity.bank_transaction;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;

@Data
@Relation(collectionRelation = "transactions", itemRelation = "transaction")
public class UserAccountTransactionDTO extends RepresentationModel<UserAccountTransactionDTO> {
    private Double amount;
    private String description;
    private Date dateTime;
    private String transactionType;

    public UserAccountTransactionDTO(BankTransaction transaction) {
        amount = transaction.getAmount();
        description = transaction.getDescription();
        dateTime = transaction.getDateTime();
        transactionType = transaction.getTransactionType().toString();
    }
}
