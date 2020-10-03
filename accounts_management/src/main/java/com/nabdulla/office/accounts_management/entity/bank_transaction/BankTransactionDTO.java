package com.nabdulla.office.accounts_management.entity.bank_transaction;

import com.nabdulla.office.accounts_management.entity.bank_account.BankAccountDTOAssemblerSupport;
import com.nabdulla.office.accounts_management.entity.bank_account.BankAccountDTO;
import com.nabdulla.office.accounts_management.entity.user.UserDTO;
import com.nabdulla.office.accounts_management.entity.user.UserDTOAssemblerSupport;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;

@Getter
@Relation(collectionRelation = "transactions", itemRelation = "transaction")
public class BankTransactionDTO extends RepresentationModel<BankTransactionDTO> {
    private Double amount;
    private String description;
    private Date dateTime;
    private String transactionType;
    private UserDTO accountant;
    private BankAccountDTO account;

    public BankTransactionDTO(BankTransaction bankTransaction) {
        amount = bankTransaction.getAmount();
        description = bankTransaction.getDescription();
        dateTime = bankTransaction.getDateTime();
        transactionType = bankTransaction.getTransactionType().name();
        accountant = new UserDTOAssemblerSupport().toModel(bankTransaction.getUser());
        account = new BankAccountDTOAssemblerSupport().toModel(bankTransaction.getAccount());
    }
}
