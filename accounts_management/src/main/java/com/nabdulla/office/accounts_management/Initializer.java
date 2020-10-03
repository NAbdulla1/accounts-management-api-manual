package com.nabdulla.office.accounts_management;

import com.nabdulla.office.accounts_management.entity.bank_account.BankAccount;
import com.nabdulla.office.accounts_management.security.User;
import com.nabdulla.office.accounts_management.repos.BankAccountRepo;
import com.nabdulla.office.accounts_management.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Initializer implements SmartInitializingSingleton {
    private final UserRepo userRepo;
    private final BankAccountRepo bankAccountRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void afterSingletonsInstantiated() {
        UUID user1id = UUID.randomUUID();
        UUID user2id = UUID.randomUUID();

        User user1 = new User(user1id, "user1", "USER 1", true, passwordEncoder.encode("1234"));
        User user2 = new User(user2id, "user2", "USER 2", true, passwordEncoder.encode("9876"));

        user1.addAuthority("READ");
        user2.addAuthority("READ");
        user2.addAuthority("WRITE");

        userRepo.save(user1);
        userRepo.save(user2);

        bankAccountRepo.save(new BankAccount("account 1"));
        bankAccountRepo.save(new BankAccount("account 2"));
    }
}
