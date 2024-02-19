package com.ccsw.tutorial.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ccsw.tutorial.exception.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NameAlreadyExistsException.class, ClientHasMaxLentGamesException.class, 
    	LoanDateGreaterThanReturnDateException.class, GameLentClientContraintException.class,
    	LoanPeriodGreaterThan14DaysException.class})
    public ResponseEntity<String> handleConflict(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
