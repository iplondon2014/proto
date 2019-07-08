package org.fcg.proto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProtoExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> handleBadMessage(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bad request");
    }
}
