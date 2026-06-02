package com.andisramja.studentmanagement.web;

import com.andisramja.studentmanagement.service.exception.EmailAlreadyExistsException;
import com.andisramja.studentmanagement.service.exception.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Trajtuesi global i gabimeve për të gjithë controller-at REST.
 * <p>
 * Anotacioni {@code @RestControllerAdvice} është një specializim i
 * {@code @ControllerAdvice} që e kthen përgjigjen drejtpërdrejt si trup JSON.
 * Çdo lloj përjashtimi përkthehet në një kod statusi HTTP të përshtatshëm.
 * </p>
 *
 * @author Andis Ramja
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trajton rastin kur studenti nuk gjendet dhe kthen statusin 404.
     *
     * @param ex përjashtimi i kapur
     * @return përgjigja e gabimit me status 404
     */
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStudentNotFound(StudentNotFoundException ex) {
        ErrorResponse body = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Nuk u gjet",
                ex.getMessage(),
                null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    /**
     * Trajton tentativën për një email të dyfishtë dhe kthen statusin 409.
     *
     * @param ex përjashtimi i kapur
     * @return përgjigja e gabimit me status 409
     */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailExists(EmailAlreadyExistsException ex) {
        ErrorResponse body = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Konflikt",
                ex.getMessage(),
                null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    /**
     * Trajton gabimet e validimit të hyrjeve dhe kthen statusin 400
     * së bashku me detajet për çdo fushë të pavlefshme.
     *
     * @param ex përjashtimi i validimit
     * @return përgjigja e gabimit me status 400 dhe hartën e gabimeve
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }
        ErrorResponse body = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Të dhëna të pavlefshme",
                "Validimi i të dhënave dështoi",
                fieldErrors);
        return ResponseEntity.badRequest().body(body);
    }

    /**
     * Trajton rastin kur rruga/burimi i kërkuar nuk ekziston dhe kthen statusin 404.
     * <p>
     * Pa këtë trajtim, kërkesat ndaj rrugëve të pamapuara (p.sh. "/" ose "/favicon.ico")
     * do të kapeshin nga trajtuesi i përgjithshëm dhe do të ktheheshin gabimisht si 500.
     * </p>
     *
     * @param ex përjashtimi i kapur
     * @return përgjigja e gabimit me status 404
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFound(NoResourceFoundException ex) {
        ErrorResponse body = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Nuk u gjet",
                "Burimi i kërkuar nuk u gjet: " + ex.getResourcePath(),
                null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    /**
     * Trajton çdo gabim tjetër të papritur dhe kthen statusin 500.
     *
     * @param ex përjashtimi i kapur
     * @return përgjigja e gabimit me status 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse body = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Gabim i brendshëm i serverit",
                ex.getMessage(),
                null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
