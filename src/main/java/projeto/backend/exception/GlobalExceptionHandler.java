package projeto.backend.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        if(ex.getMessage().contains("cpf")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF já cadastrado.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Violação de integridade nos dados.");
    }
}
