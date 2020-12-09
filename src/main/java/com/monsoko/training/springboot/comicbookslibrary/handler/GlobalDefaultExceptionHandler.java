package com.monsoko.training.springboot.comicbookslibrary.handler;


import org.hibernate.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.monsoko.training.springboot.comicbookslibrary.domain.exception.RessourceNotFoundException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(value = {
            EntityNotFoundException.class,
            ObjectNotFoundException.class,
            DataIntegrityViolationException.class,
            EntityExistsException.class,
            OptimisticLockingFailureException.class,
            EmptyResultDataAccessException.class,
            RessourceNotFoundException.class
    })
    
    public void handleJpaException(Exception ex, HttpServletResponse response) throws IOException {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex instanceof EntityNotFoundException || ex instanceof ObjectNotFoundException || ex instanceof RessourceNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } 
        else if (ex instanceof DataIntegrityViolationException || ex instanceof EntityExistsException || ex instanceof OptimisticLockingFailureException) {
            status = HttpStatus.CONFLICT;
        }
        else if (ex instanceof EmptyResultDataAccessException) {
            status = HttpStatus.BAD_REQUEST;
        }

        response.sendError(status.value(), ex.getMessage());
    }

    @ExceptionHandler(value = {
            IllegalArgumentException.class,
            ConstraintViolationException.class,
            RollbackException.class
    })
    public void handleValidationException(Exception ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
}