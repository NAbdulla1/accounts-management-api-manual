package com.nabdulla.office.accounts_management.entity.bank_transaction;

import com.nabdulla.office.accounts_management.security.User;
import com.nabdulla.office.accounts_management.entity.bank_account.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    @Min(0)
    private Double amount;

    @Column(length = 5000, nullable = false)
    @Length(max = 5000)
    @NotBlank
    private String description;

    @CreationTimestamp
    @Column(name = "date_time", nullable = false, updatable = false)
    private Date dateTime;

    @Column(name = "transaction_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountant_id")
    private User user;

    @JoinColumn(name = "bank_account_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private BankAccount account;

    public enum TransactionType {
        DEBIT, CREDIT
    }
}
