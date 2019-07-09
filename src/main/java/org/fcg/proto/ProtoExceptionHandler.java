package org.fcg.proto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class ProtoExceptionHandler {
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleError(Exception e) {
        return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<String> handleBadMessage(Exception e) {
        return getResponseEntity(HttpStatus.BAD_REQUEST, "bad request");
    }

    @ExceptionHandler({ProtoImageNotFoundException.class, IOException.class})
    public ResponseEntity<String> handleNotFound(Exception e) {
        return getResponseEntity(HttpStatus.NOT_FOUND, "not found");
    }

    private ResponseEntity<String> getResponseEntity(HttpStatus httpStatus, String message) {
        String content = null;
        try {
            content = OBJECT_MAPPER.writeValueAsString(new Error(httpStatus.value(), message));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("unable to serialise json");
        }
        return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON).body(content);
    }

    class Error {
        private int status;
        private String message;

        public Error(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }
}
