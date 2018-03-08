package com.mancini.contacts.api.jpa.advice;

import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
@RequestMapping(produces = "application/json")
@ResponseBody
public class GlobalErrorHandler {



    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String,Object>> pathNotFound(final Exception e) {

        Map<String,Object> errorInfo = new LinkedHashMap<>();
        errorInfo.put("timestamp", new Date());
        errorInfo.put("errorMessage",e.getMessage());
        errorInfo.put("httpCode", HttpStatus.NOT_FOUND.value());
        errorInfo.put("httpStatus", HttpStatus.NOT_FOUND.getReasonPhrase());
        return new ResponseEntity<Map<String,Object>>(errorInfo, HttpStatus.NOT_FOUND);

    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> resourceNotFound(final Exception e) {

        Map<String,Object> errorInfo = new LinkedHashMap<>();
        errorInfo.put("timestamp", new Date());
        errorInfo.put("errorMessage",e.getMessage());
        errorInfo.put("httpCode", HttpStatus.NOT_FOUND.value());
        errorInfo.put("httpStatus", HttpStatus.NOT_FOUND.getReasonPhrase());
        return new ResponseEntity<Map<String,Object>>(errorInfo, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(RepositoryConstraintViolationException.class)
    public ResponseEntity<Map<String,Object>> validationError(final RepositoryConstraintViolationException e) {
        Map<String,Object> errorInfo = new LinkedHashMap<>();
        errorInfo.put("timestamp", new Date());
        errorInfo.put("errorMessage",e.getErrors().toString());
        errorInfo.put("httpCode", HttpStatus.BAD_REQUEST.value());
        errorInfo.put("httpStatus", HttpStatus.BAD_REQUEST.getReasonPhrase());
        return new ResponseEntity<Map<String,Object>>(errorInfo, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> oops(final Exception e) {

        Map<String,Object> errorInfo = new LinkedHashMap<>();
        errorInfo.put("timestamp", new Date());
        errorInfo.put("errorMessage",e.getMessage());
        errorInfo.put("httpCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorInfo.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return new ResponseEntity<Map<String,Object>>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);

    }


}
