package com.example.translator.exceptions;

import com.example.translator.configuration.SupportedLanguages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>("Язык " + ex.getMessage() +
                " не поддерживается переводчиком Google. Поддерживаемые языки: " +
                SupportedLanguages.VALID_LANGUAGE_CODES, HttpStatus.BAD_REQUEST);
    }
}
