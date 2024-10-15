package ci.org.recycle.web.resources;

import ci.org.recycle.web.exceptions.*;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolationException(
            DataIntegrityViolationException ex) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Data Integrity Violation");

        if (ex.getCause() instanceof ConstraintViolationException cve) {
            if (cve.getConstraintName() != null) {
                if (cve.getConstraintName().contains("UK_")) {
                    problemDetail.setDetail("Une ressource avec cette clé unique existe déjà.");
                } else if (cve.getConstraintName().contains("FK_")) {
                    problemDetail.setDetail("Violation de contrainte de clé étrangère.");
                } else {
                    problemDetail.setDetail("Violation de contrainte : " + cve.getConstraintName());
                }
            }
        } else {
            problemDetail.setDetail("Une erreur d'intégrité des données s'est produite.");
        }

        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("exception", ex.getClass().getSimpleName());

        return problemDetail;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
                       CitizenNotFoundException.class,
                       CollectionPointNotFoundException.class,
                       PickerNotFoundException.class,
                       MyRoleNotFoundException.class,
                       MyUserNotFoundException.class,
                       RepairerNotFoundException.class,
                       CollectionPointNotFoundException.class,
                       TypeWasteNotFoundException.class
    })
    public ProblemDetail handleNotFoundException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("exception", ex.getClass().getSimpleName());
        return problemDetail;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationExceptions(MethodArgumentNotValidException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Validation Field Error");

        BindingResult bindingResult = exception.getBindingResult();
        Map<String, String> errors = new HashMap<>();

        for (ObjectError error : bindingResult.getAllErrors()) {
            if (error instanceof FieldError fieldError) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            } else {
                errors.put(error.getObjectName(), error.getDefaultMessage());
            }
        }

        problemDetail.setProperty("errors", errors);
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("exception", exception.getClass().getSimpleName());
        return problemDetail;
    }


}
