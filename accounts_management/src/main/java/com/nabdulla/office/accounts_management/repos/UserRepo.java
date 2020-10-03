package com.nabdulla.office.accounts_management.repos;

import com.nabdulla.office.accounts_management.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

//@RepositoryRestResource(path = "users", collectionResourceRel = "users", itemResourceRel = "user")
public interface UserRepo extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}
