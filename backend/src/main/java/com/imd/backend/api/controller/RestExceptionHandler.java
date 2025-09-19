package com.imd.backend.api.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.imd.backend.api.dto.RestResponseMessage;
import com.imd.backend.domain.exception.BadRequestException;
import com.imd.backend.domain.exception.ForbiddenException;
import com.imd.backend.domain.exception.InvalidEntityAttributesException;
import com.imd.backend.domain.exception.NotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

/**
 * Controller Advice refatorado para lidar com exceções de forma declarativa e
 * limpa,
 * utilizando um método para cada tipo de erro.
 */
@RestControllerAdvice // Usar @RestControllerAdvice é ideal para APIs REST
public class RestExceptionHandler {

    /**
     * Lida com a exceção quando um recurso não é encontrado.
     * Retorna HTTP 404 (Not Found).
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RestResponseMessage handleNotFoundException(NotFoundException ex) {
        return new RestResponseMessage(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND.value(),
                ex.getMessage());
    }

    /**
     * Lida com várias exceções que indicam uma requisição malformada.
     * Retorna HTTP 400 (Bad Request).
     */
    @ExceptionHandler({
            BadRequestException.class,
            InvalidFormatException.class,
            DateTimeParseException.class,
            IllegalArgumentException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponseMessage handleBadRequestExceptions(Exception ex) {
        return new RestResponseMessage(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value(),
                ex.getMessage());
    }

    /**
     * Lida especificamente com erros de validação de DTOs (@Valid).
     * Retorna HTTP 400 (Bad Request) com uma mensagem detalhada dos campos.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponseMessage handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> String.format("'%s': %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.joining("; "));

        String message = "Erro de validação: " + errors;
        return new RestResponseMessage(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value(),
                message);
    }

    @ExceptionHandler(InvalidEntityAttributesException.class)
    public RestResponseMessage handleInvalidEntityAttrs(InvalidEntityAttributesException ex) {
        return new RestResponseMessage(ex.getErrors(), HttpStatus.BAD_REQUEST.value(), ex.getMessage());        
    }

    /**
     * Lida com violações de constraints do banco de dados (ex: campo único).
     * Retorna HTTP 409 (Conflict), que é semanticamente mais adequado que 400.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public RestResponseMessage handleConstraintViolationException(ConstraintViolationException ex) {
        return new RestResponseMessage(HttpStatus.CONFLICT.getReasonPhrase(), HttpStatus.CONFLICT.value(),
                "Violação de constraint de dados: " + ex.getConstraintName());
    }

    /**
     * Lida com exceções de acesso negado.
     * Retorna HTTP 403 (Forbidden).
     */
    @ExceptionHandler({ ForbiddenException.class, InvalidCsrfTokenException.class })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public RestResponseMessage handleForbiddenException(Exception ex) {
        return new RestResponseMessage(HttpStatus.FORBIDDEN.getReasonPhrase(), HttpStatus.FORBIDDEN.value(),
                ex.getMessage());
    }

    /**
     * Lida com erros em uploads de arquivos.
     * Retorna HTTP 400 (Bad Request).
     */
    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponseMessage handleMultipartException(MultipartException ex) {
        String message = "A requisição deve ser do tipo multipart/form-data e conter um arquivo válido.";
        return new RestResponseMessage(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value(),
                message);
    }

    /**
     * Lida com erros desconhecidos.
     * Retorna HTTP 500 (Internal Server Error).
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResponseMessage handleGenericException(Exception ex) {
        return new RestResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage());
    }    
}