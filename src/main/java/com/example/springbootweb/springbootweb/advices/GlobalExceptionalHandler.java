package com.example.springbootweb.springbootweb.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionalHandler {

//    @ExceptionHandler(NoSuchElementException.class)
//    public ResponseEntity<ApiError> handleResourceNotFound(NoSuchElementException exception){
////        return new ResponseEntity<>("Resource Not Found", HttpStatus.NOT_FOUND);
//            ApiError apiError= ApiError.builder().status(HttpStatus.NOT_FOUND).message("Resource Not Found").build();
//            return  new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(RuntimeException exception){

        ApiError apiError= ApiError.builder().
                status(HttpStatus.NOT_FOUND).
                message(exception.getMessage()).
                build();
        return  new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleInputValidationError(MethodArgumentNotValidException exception){
       List<String> errors= exception.
                getBindingResult().
                getAllErrors().
                stream().
                map(error -> error.getDefaultMessage()).
                collect(Collectors.toList());

        ApiError apiError= ApiError.builder().
                status(HttpStatus.BAD_REQUEST).
                message("Input Validations Error ").
                subError(errors).
                build();
        return  new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
