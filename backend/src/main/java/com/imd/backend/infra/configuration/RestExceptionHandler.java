package com.imd.backend.infra.configuration;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.imd.backend.api.dto.RestResponseMessage;
import com.imd.backend.domain.exception.BadRequestException;
import com.imd.backend.domain.exception.ForbbidenException;
import com.imd.backend.domain.exception.NotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({
            NotFoundException.class,
            BadRequestException.class,
            ForbbidenException.class,
            InvalidFormatException.class,
            IllegalArgumentException.class,
            DateTimeParseException.class,
            ConstraintViolationException.class,
            InvalidCsrfTokenException.class,
            MethodArgumentNotValidException.class,
            MultipartException.class
    })
    public ResponseEntity<RestResponseMessage> handleCustomExceptions(Exception ex, WebRequest request) {
        if (ex instanceof NotFoundException) {
            return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
        } else if (ex instanceof BadRequestException || ex instanceof InvalidFormatException
                || ex instanceof DateTimeParseException) {
            return buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
        } else if (ex instanceof ForbbidenException) {
            return buildError(HttpStatus.FORBIDDEN, ex.getMessage(), request);
        } else if (ex instanceof MethodArgumentNotValidException) {
            StringBuilder sb = new StringBuilder("Erro de validação: ");
            ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors().forEach(fieldError -> {
                sb.append(fieldError.getField())
                        .append(" -> ")
                        .append(fieldError.getDefaultMessage())
                        .append("; ");
            });
            return buildError(HttpStatus.BAD_REQUEST, sb.toString(), request);
        } else if (ex instanceof MultipartException) {
            return buildError(HttpStatus.BAD_REQUEST,
                    "A requisição deve ser do tipo multipart/form-data e conter um arquivo.",
                    request);
        } else if (ex instanceof IllegalArgumentException) {
            return buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
        } else {
            return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error: " + ex.getMessage(), request);
        }
    }

    private ResponseEntity<RestResponseMessage> buildError(HttpStatus status, String message, WebRequest request) {
        return ResponseEntity.status(status).body(
                new RestResponseMessage(
                        status.getReasonPhrase(),
                        status.value(),
                        message
                )
        );
    }

}
