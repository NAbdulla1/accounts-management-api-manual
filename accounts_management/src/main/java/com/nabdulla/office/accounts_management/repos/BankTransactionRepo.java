package com.nabdulla.office.accounts_management.repos;

import com.nabdulla.office.accounts_management.entity.bank_transaction.BankTransaction;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

//@RepositoryRestResource(path = "transactions", collectionResourceRel = "transactions", itemResourceRel = "trx")
public interface BankTransactionRepo extends PagingAndSortingRepository<BankTransaction, Long> {
    Iterable<BankTransaction> findBankTransactionByUser_UsernameAndAccount_Id(String username, Long accountId);
}
