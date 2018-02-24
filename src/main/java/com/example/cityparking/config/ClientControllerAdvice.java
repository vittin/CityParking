package com.example.cityparking.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.AccessControlException;

@ControllerAdvice(basePackages = "com.example.cityparking.customer")
public class ClientControllerAdvice extends ResponseEntityExceptionHandler {

    private final HttpHeaders headers;

    public ClientControllerAdvice() {
        this.headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @ExceptionHandler(value = { BadCurrencyCodeException.class })
    protected ResponseEntity<Object> handleBadCurrency(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Currency code: " + ex.getMessage() + "is not valid! Currency should be 3letter shortcut!";
        String response = buildJson(bodyOfResponse);
        return handleExceptionInternal(ex, response,
                headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { AccessControlException.class })
    //todo: spring security is good for this
    protected ResponseEntity<Object> handleWrongParkId(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "You have no permission to access event with this id! " + ex.getMessage();
        String response = buildJson(bodyOfResponse);
        return handleExceptionInternal(ex, response,
                headers, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = { IllegalStateException.class })
    //todo: spring security is good for this
    protected ResponseEntity<Object> handleNoMoney(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "No money to perform payment! " + ex.getMessage();
        String response = buildJson(bodyOfResponse);
        return handleExceptionInternal(ex, response,
                headers, HttpStatus.PAYMENT_REQUIRED, request);
    }

    private String buildJson(String bodyOfResponse) {
        return "{\"error\":\""+bodyOfResponse+"\"}";
    }




}
