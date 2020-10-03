package com.nabdulla.office.accounts_management;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApiError {
    private final HttpStatus status;
    private final String message;
    private List<String> errors = new ArrayList<>();
}
