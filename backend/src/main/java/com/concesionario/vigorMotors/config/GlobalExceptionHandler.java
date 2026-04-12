package com.concesionario.vigorMotors.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.concesionario.vigorMotors.dto.MessageResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<MessageResponseDTO> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
        return error("Método no permitido: " + e.getMethod() + " no está soportado en esta ruta");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<MessageResponseDTO> handleNotFound(NoHandlerFoundException e) {
        return error("Ruta no encontrada: " + e.getRequestURL());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageResponseDTO> handleValidation(MethodArgumentNotValidException e) {
        String mensaje = e.getBindingResult().getFieldErrors()
                .stream()
                .map(f -> f.getDefaultMessage())
                .findFirst()
                .orElse("Error de validación");
        return error(mensaje);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponseDTO> handleGeneral(Exception e) {
        return error("Error interno: " + e.getMessage());
    }

    private ResponseEntity<MessageResponseDTO> error(String mensaje) {
        MessageResponseDTO response = new MessageResponseDTO();
        response.setMessage(mensaje);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}