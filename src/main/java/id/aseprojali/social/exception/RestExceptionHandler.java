package id.aseprojali.social.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by avew on 9/13/17.
 */
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(FriendException.class)
    public ResponseEntity<ErrorResponse> exceptionToDoHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        error.setStatus(Boolean.FALSE);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());
        error.setStatus(Boolean.FALSE);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
