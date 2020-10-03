package com.nabdulla.office.accounts_management.entity.user;

import com.nabdulla.office.accounts_management.entity.bank_account.BankAccountDTO;
import com.nabdulla.office.accounts_management.entity.bank_account.BankAccountDTOAssemblerSupport;
import com.nabdulla.office.accounts_management.entity.bank_transaction.BankTransaction;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;

@Getter
@Relation(collectionRelation = "transactions", itemRelation = "transaction")
public class UserTransactionDTO extends RepresentationModel<UserTransactionDTO> {
    private Double amount;
    private String description;
    private Date dateTime;
    private String transactionType;
    private BankAccountDTO account;

    public UserTransactionDTO(BankTransaction transaction) {
        amount = transaction.getAmount();
        description = transaction.getDescription();
        dateTime = transaction.getDateTime();
        transactionType = transaction.getTransactionType().toString();
        account = new BankAccountDTOAssemblerSupport().toModel(transaction.getAccount());
    }
}
