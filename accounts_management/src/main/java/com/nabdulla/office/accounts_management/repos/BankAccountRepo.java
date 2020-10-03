package com.nabdulla.office.accounts_management.repos;

import com.nabdulla.office.accounts_management.entity.bank_account.BankAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

//@RepositoryRestResource(path = "accounts", collectionResourceRel = "accounts", itemResourceRel = "bank_account")
public interface BankAccountRepo extends CrudRepository<BankAccount, Long> {
    Optional<BankAccount> findBankAccountByAccountNameIgnoreCase(String accountName);
}
