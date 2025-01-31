package com.example.booking.web.controller;

import com.example.booking.web.model.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler (Exception ex){

        if (ex instanceof EntityNotFoundException) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(ex.getMessage()));
        }

         if (ex instanceof MethodArgumentNotValidException) {

            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
            BindingResult bindingResult = exception.getBindingResult();
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();

            String errorMessage = String.join("; ", errorMessages);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(errorMessage));
        }

         if (ex instanceof IllegalArgumentException) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                     .body(new ErrorResponse(ex.getMessage()));
         }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(ex.getMessage()));

    }
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<ErrorResponse> notFoundExceptionHandler (EntityNotFoundException ex){
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(new ErrorResponse(ex.getMessage()));
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponse> notValid (MethodArgumentNotValidException ex){
//        BindingResult bindingResult = ex.getBindingResult();
//        List<String> errorMessages = bindingResult.getAllErrors()
//                .stream()
//                .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                .toList();
//
//        String errorMessage = String.join("; ", errorMessages);
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(new ErrorResponse(errorMessage));
//    }
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<ErrorResponse> illegalArgumentExceptionHandler (IllegalArgumentException ex){
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> unprocessedExceptionHandler (Exception ex){
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ex.getMessage()));
//    }
}
