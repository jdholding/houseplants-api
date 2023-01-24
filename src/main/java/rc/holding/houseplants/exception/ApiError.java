package rc.holding.houseplants.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Value;

@Value
public class ApiError {
    LocalDateTime timestamp; 
    int status; 
    String error; 
    String message; 

    public ApiError(HttpStatus status, String message) {
        this.timestamp = LocalDateTime.now(); 
        this.status = status.value(); 
        this.error = status.getReasonPhrase(); 
        this.message = message; 
    }
}
