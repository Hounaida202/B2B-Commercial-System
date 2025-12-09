
package com.example.demo.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorException> handleNotFound(NotFoundException ex, WebRequest request) {
        ErrorException err = new ErrorException(
                ex.getMessage(),
                "Not Found",
                404,
                request.getDescription(false)
        );
        return ResponseEntity.status(404).body(err);
    }


    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorException> handleUnauthorized(UnauthorizedException ex, WebRequest request) {
        ErrorException err = new ErrorException(
                ex.getMessage(),
                "Unauthorized",
                401,
                request.getDescription(false)
        );
        return ResponseEntity.status(401).body(err);
    }


    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorException> handleForbidden(ForbiddenException ex, WebRequest request) {
        ErrorException err = new ErrorException(
                ex.getMessage(),
                "Forbidden",
                403,
                request.getDescription(false)
        );
        return ResponseEntity.status(403).body(err);
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorException> handleBadRequest(BadRequestException ex, WebRequest request) {
        ErrorException err = new ErrorException(
                ex.getMessage(),
                "Bad Request",
                400,
                request.getDescription(false)
        );
        return ResponseEntity.status(400).body(err);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorException> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        ErrorException err = new ErrorException(
                ex.getMessage(),
                "Bad Request",
                400,
                request.getDescription(false)
        );
        return ResponseEntity.status(400).body(err);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(msg);
    }


//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorException> handleGenericException(Exception ex, WebRequest request) {
//        ErrorException err = new ErrorException(
//                "Une erreur interne s'est produite",
//                "Internal Server Error",
//                500,
//                request.getDescription(false)
//        );
//        return ResponseEntity.status(500).body(err);
//    }
}