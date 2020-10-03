package com.nabdulla.office.accounts_management.entity.bank_account;

import com.nabdulla.office.accounts_management.entity.bank_transaction.BankTransaction;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Getter
public class AccountTransactionDTO extends RepresentationModel<AccountTransactionDTO> {
    private Double amount;
    private String description;
    private Date dataTime;
    private String transactionType;
    private String accountant;

    public AccountTransactionDTO(BankTransaction bankTransaction) {
        amount = bankTransaction.getAmount();
        description = bankTransaction.getDescription();
        dataTime = bankTransaction.getDateTime();
        transactionType = bankTransaction.getTransactionType().name();
        accountant = bankTransaction.getUser() == null ? "default User" : bankTransaction.getUser().getFullName();
    }
}
