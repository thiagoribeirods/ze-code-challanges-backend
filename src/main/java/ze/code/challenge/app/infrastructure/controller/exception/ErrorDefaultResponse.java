package ze.code.challenge.app.infrastructure.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorDefaultResponse {
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String error;
    private String message;

    public ErrorDefaultResponse() {}
}
