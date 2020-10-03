package com.nabdulla.office.accounts_management.entity.bank_account;

import com.nabdulla.office.accounts_management.entity.bank_transaction.BankTransaction;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;

@Entity(name = "accounts")
@Data
@NoArgsConstructor
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "account_name", nullable = false, unique = true)
    @Length(min = 5, max = 255)
    private String accountName;

    @Column(name = "amount", nullable = false)
    @Min(0)
    private double amount = 0;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
    private List<BankTransaction> transactions;

    public BankAccount(String accountName) {
        this.accountName = accountName;
    }

    public void performTransaction(BankTransaction newTrx) throws InvalidTransactionTypeException, InsufficientAccountBalanceException {
        switch (newTrx.getTransactionType()) {
            case CREDIT:
                addAmount(newTrx.getAmount());
                break;
            case DEBIT:
                subtractAmount(newTrx.getAmount());
                break;
            default:
                throw new InvalidTransactionTypeException("Invalid transaction Type");
        }
    }

    private void addAmount(double changeAmount) {
        setAmount(getAmount() + changeAmount);
    }

    private void subtractAmount(Double changeAmount) throws InsufficientAccountBalanceException {
        if (getAmount() >= changeAmount)
            setAmount(getAmount() - changeAmount);
        else
            throw new InsufficientAccountBalanceException("This Account has not enough balance to withdraw.");
    }

    public static class InsufficientAccountBalanceException extends RuntimeException {
        public InsufficientAccountBalanceException(String message) {
            super(message);
        }
    }

    public static class InvalidTransactionTypeException extends RuntimeException {
        public InvalidTransactionTypeException(String invalid_transaction_type) {
            super(invalid_transaction_type);
        }
    }
}
