package com.nabdulla.office.accounts_management.entity.user;

import com.nabdulla.office.accounts_management.security.User;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Relation(collectionRelation = "users", itemRelation = "user")
public class UserDTO extends RepresentationModel<UserDTO> {
    private String username;
    private String fullName;
    private boolean enabled;

    public UserDTO(User user) {
        username = user.getUsername();
        fullName = user.getFullName();
        enabled = user.isEnabled();
    }
}
