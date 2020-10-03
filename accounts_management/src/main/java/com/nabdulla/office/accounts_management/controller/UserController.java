package com.nabdulla.office.accounts_management.controller;

import com.nabdulla.office.accounts_management.entity.user.UserDTO;
import com.nabdulla.office.accounts_management.entity.user.UserDTOAssemblerSupport;
import com.nabdulla.office.accounts_management.entity.user.UserTransactionDTO;
import com.nabdulla.office.accounts_management.entity.user.UserTransactionDTOAssemblerSupport;
import com.nabdulla.office.accounts_management.repos.UserRepo;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping(produces = "application/json")
    public CollectionModel<UserDTO> getUsers() {
        var users = userRepo.findAll();
        var dtos = new UserDTOAssemblerSupport().toCollectionModel(users);
        dtos.add(linkTo(methodOn(UserController.class).getUsers()).withSelfRel());

        dtos.add(
                linkTo(methodOn(this.getClass()).getUser("USERNAME")).withRel("user"),
                linkTo(methodOn(this.getClass()).getUserTransactions("USERNAME")).withRel("user-transactions")
        );
        return dtos;
    }

    @GetMapping(path = "/{username}", produces = "application/json")
    public ResponseEntity<UserDTO> getUser(@PathVariable("username") String username) {
        var user = userRepo.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        var dto = new UserDTOAssemblerSupport().toModel(user);
        dto.add(linkTo(methodOn(UserController.class).getUser(username)).withSelfRel());

        dto.add(
                linkTo(methodOn(this.getClass()).getUserTransactions("USERNAME")).withRel("user-transactions"),
                linkTo(methodOn(this.getClass()).getUsers()).withRel("users")
        );
        return ResponseEntity.ok(dto);
    }

    @GetMapping(path = "/{username}/transactions", produces = "application/json")
    public CollectionModel<UserTransactionDTO> getUserTransactions(@PathVariable("username") String username) {
        var user = userRepo.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        var dtos = new UserTransactionDTOAssemblerSupport().toCollectionModel(user.getTransactions());
        dtos.add(linkTo(methodOn(UserController.class).getUserTransactions(username)).withSelfRel());

        dtos.add(
                linkTo(methodOn(this.getClass()).getUser("USERNAME")).withRel("user"),
                linkTo(methodOn(this.getClass()).getUsers()).withRel("users")
        );
        return dtos;
    }
}
