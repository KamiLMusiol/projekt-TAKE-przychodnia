package przychodnia.baza.backend.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> blad = new HashMap<>();
        blad.put("timestamp", LocalDateTime.now());
        blad.put("status", 500);
        blad.put("error", "Błąd serwera");
        blad.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(blad);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        Map<String, Object> blad = new HashMap<>();
        blad.put("timestamp", LocalDateTime.now());
        blad.put("status", 400);
        blad.put("error", "Błąd zapytania");
        blad.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(blad);
    }
    
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(NoSuchElementException ex) {
        Map<String, Object> blad = new HashMap<>();
        blad.put("timestamp", LocalDateTime.now());
        blad.put("status", 404);
        blad.put("error", "Nie znaleziono");
        blad.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(blad);
    }
}