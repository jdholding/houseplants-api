package rc.holding.houseplants.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException e) {
        var status = HttpStatus.NOT_FOUND; 
        var error = new ApiError(status, e.getMessage()); 
        return new ResponseEntity<>(error, status); 
    }
    
}
