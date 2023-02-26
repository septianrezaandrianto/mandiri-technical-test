package com.technical.testmandiri.controller.advice;

import com.technical.testmandiri.constant.Constant;
import com.technical.testmandiri.exception.ConflictException;
import com.technical.testmandiri.exception.InvalidException;
import com.technical.testmandiri.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@RestControllerAdvice
public class UserControllerAdvice {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidException.class)
    public Map<String, Object> invalidExceptionHandler(InvalidException ex) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("code", ex.getCode());
        errorMap.put("status", ex.getStatus());
        errorMap.put("message", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    public Map<String, Object> conflictExceptionHandler(ConflictException ex) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("code", ex.getCode());
        errorMap.put("status", ex.getStatus());
        errorMap.put("message", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public Map<String, Object> notFoundExceptionHandler(NotFoundException ex) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("code", Constant.ERROR_404_CODE);
        errorMap.put("status", HttpStatus.NOT_FOUND.getReasonPhrase());
        errorMap.put("message", Constant.ERROR_404_MESSAGE);
        return errorMap;
    }
}
