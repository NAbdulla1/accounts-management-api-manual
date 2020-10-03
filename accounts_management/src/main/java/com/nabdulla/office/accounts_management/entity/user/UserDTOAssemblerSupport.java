package com.nabdulla.office.accounts_management.entity.user;

import com.nabdulla.office.accounts_management.controller.UserController;
import com.nabdulla.office.accounts_management.security.User;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class UserDTOAssemblerSupport extends RepresentationModelAssemblerSupport<User, UserDTO> {

    public UserDTOAssemblerSupport() {
        super(UserController.class, UserDTO.class);
    }

    @Override
    public UserDTO toModel(User entity) {
        var dto = new UserDTO(entity);
        dto.add(linkTo(methodOn(UserController.class).getUser(entity.getUsername())).withSelfRel());
        return dto;
    }
}
