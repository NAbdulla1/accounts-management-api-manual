package com.nabdulla.office.accounts_management.controller;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/")
public class IndexController {
    @GetMapping(produces = "application/json")
    public ResponseEntity<RepresentationModel<?>> getLinks() {
        return ResponseEntity.ok(
                RepresentationModel.of(null,
                        Arrays.asList(
                                linkTo(IndexController.class).withSelfRel(),
                                linkTo(UserController.class).withRel("users"),
                                linkTo(BankAccountResource.class).withRel("accounts"),
                                linkTo(BankTransactionResource.class).withRel("transactions")
                        )
                )
        );
    }
}
