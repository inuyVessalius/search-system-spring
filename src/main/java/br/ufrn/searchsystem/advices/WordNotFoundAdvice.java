package br.ufrn.searchsystem.advices;

import br.ufrn.searchsystem.exceptions.WordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class WordNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(WordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(WordNotFoundException ex) {
        return ex.getMessage();
    }
}