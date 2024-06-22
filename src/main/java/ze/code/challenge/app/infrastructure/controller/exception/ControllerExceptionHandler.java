package ze.code.challenge.app.infrastructure.controller.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {

    private final ErrorDefaultResponse errorDefaultResponse = new ErrorDefaultResponse();

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        errorDefaultResponse.setTimestamp(LocalDateTime.now());
        errorDefaultResponse.setStatus(HttpStatus.BAD_REQUEST);
        errorDefaultResponse.setMessage("Error processing your request. Please check all the fields entered.");
        errorDefaultResponse.setError(String.valueOf(e.getAllErrors()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.errorDefaultResponse);
    }

}
