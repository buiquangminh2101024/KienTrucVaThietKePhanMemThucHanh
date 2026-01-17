package iuh.fit.se.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi không xác định");
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex) {
        System.err.println(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi không xác định");
    }
}
