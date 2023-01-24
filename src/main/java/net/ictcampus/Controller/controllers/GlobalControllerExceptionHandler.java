package net.ictcampus.Controller.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<String> handleConversion(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleResponseStatusException(RuntimeException ex) {
        if (ex instanceof ResponseStatusException) {
            return new ResponseEntity<>(ex.getMessage(), ((ResponseStatusException) ex).getStatus());
        } else {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Hidden
    public Map<String, List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            if (!errors.containsKey(fieldName))
                errors.put(fieldName, new ArrayList<>());

            errors.get(fieldName).add(errorMessage);
        });
        return errors;
    }
}
