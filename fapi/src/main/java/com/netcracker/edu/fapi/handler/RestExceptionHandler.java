package com.netcracker.edu.fapi.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public void handleException(Exception e, HttpServletResponse response) throws IOException {
        e.printStackTrace();
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<String> handleHttpStatusCodeException(HttpStatusCodeException e) {
        String msg = e.getResponseBodyAsString();
        return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public void handleResponseStatusException(ResponseStatusException e, HttpServletResponse response)
            throws IOException {
        response.sendError(e.getStatus().value(), e.getReason());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public void handleBadCredentialsException(BadCredentialsException e, HttpServletResponse response)
            throws IOException {
        response.sendError(HttpStatus.UNAUTHORIZED.value(), "Wrong email or password");
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public void handleAuthenticationException(InternalAuthenticationServiceException e, HttpServletResponse response)
            throws IOException {
        if (e.getCause() instanceof HttpStatusCodeException) {
            HttpStatusCodeException cause = (HttpStatusCodeException) e.getCause();
            if (cause.getStatusCode() == HttpStatus.NOT_FOUND) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Wrong email or password");
            }
        } else {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Cannot authenticate");
        }
    }
}
