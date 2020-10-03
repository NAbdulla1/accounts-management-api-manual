package com.nabdulla.office.accounts_management;

import com.nabdulla.office.accounts_management.entity.bank_account.BankAccount;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        var error = new ApiError(status, "Error creating " + result.getObjectName());
        for (FieldError fieldError : result.getFieldErrors()) {
            error.getErrors().add("'" + fieldError.getField() + "' : " + fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(error, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var error = new ApiError(status, ex.getMostSpecificCause().getMessage());
        return new ResponseEntity<>(error, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ApiError(status, "Can't Read Request properly, Please recheck Request"), status);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<ApiError> handleEntityNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {EntityExistsException.class})
    protected ResponseEntity<ApiError> accountAlreadyExists(EntityExistsException exception, WebRequest request) {
        var error = new ApiError(HttpStatus.CONFLICT, exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<ApiError> argumentConversionException(MethodArgumentTypeMismatchException ex, WebRequest req) {
        var error = new ApiError(HttpStatus.BAD_REQUEST, ex.getMostSpecificCause().getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(BankAccount.InsufficientAccountBalanceException.class)
    public ResponseEntity<ApiError> handleInsufficientAccountBalanceException(BankAccount.InsufficientAccountBalanceException ex, WebRequest req) {
        return new ResponseEntity<>(new ApiError(HttpStatus.NOT_ACCEPTABLE, ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
    }
}
