package com.nabdulla.office.accounts_management.security;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "user_authorities")
@Data
@NoArgsConstructor
public class UserAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String authority;

    @ManyToOne
    private User forUser;

    public UserAuthority(String authority, User user) {
        this.authority = authority;
        this.forUser = user;
    }
}
