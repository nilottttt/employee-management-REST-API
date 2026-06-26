package ems_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    //This method catches the specific exception thrown when @Valid fails
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();

        //Loop through all the broken rules and put them in a Map (Key: Field Name, Value: Error Message)
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        //Return the clean map as a JSON response with a 400 Bad Request Status
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // This catches your custom ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex) {

        Map<String, String> errorResponse = new HashMap<>();

        // We use "message" as the key, and the exception's custom message as the value
        errorResponse.put("message", ex.getMessage());

        // Return the map as JSON with a 404 Not Found status
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    //Catch-All for any unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex){

        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("message", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
