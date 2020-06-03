package com.counterapi.rest.exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ControllerAdvice
public class TextSearchControllerAdvice extends ResponseEntityExceptionHandler{


    @ExceptionHandler({DataNotFoundException.class})
    public ResponseEntity<Object> dataNotFoundException(DataNotFoundException e, WebRequest request) {
    	Map<String, Object> body = new LinkedHashMap<>();
    	body.put("status", "404 Not Found");
        body.put("timestamp", LocalDateTime.now());
        body.put("message", e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
   
    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<Object> validationException(ValidationException e, WebRequest request) {
    	Map<String, Object> body = new LinkedHashMap<>();
    	body.put("status", "400 Bad Request");
        body.put("timestamp", LocalDateTime.now());
        body.put("message", e.getMessage());
        
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
   

}
