package com.nabdulla.office.accounts_management.security;

import com.nabdulla.office.accounts_management.entity.bank_transaction.BankTransaction;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    private UUID id;

    @Column(unique = true, name = "username", nullable = false)
    private String username;

    @Column(nullable = false, name = "full_name")
    private String fullName;

    @Column(name = "is_enabled", nullable = false)
    private boolean enabled;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<BankTransaction> transactions = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "forUser")
    private List<UserAuthority> userAuthorities = new ArrayList<>();

    public User(User user) {
        this.enabled = user.enabled;
        this.fullName = user.fullName;
        this.id = user.id;
        this.transactions = user.transactions;
        this.username = user.username;
        this.userAuthorities = user.userAuthorities;
        this.password = user.password;
    }

    public User(UUID id, String username, String fullName, boolean enabled, String password) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.enabled = enabled;
        this.password = password;
    }

    public void addAuthority(String authority) {
        userAuthorities.add(new UserAuthority(authority, this));
    }
}
