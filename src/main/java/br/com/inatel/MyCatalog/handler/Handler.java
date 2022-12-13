package br.com.inatel.MyCatalog.handler;

import br.com.inatel.MyCatalog.exception.ShowNotFoundException;
import br.com.inatel.MyCatalog.model.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Handler {

    @ExceptionHandler(ShowNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorDto generateException(ShowNotFoundException e){
        return ErrorDto.builder()
                .type("ShowNotFoundException")
                .title("Show not found or doesn't exist")
                .httpStatus(HttpStatus.NOT_FOUND)
                .detail("Show not found or doesn't exist. Check if the name was written correctly.")
                .instance("")
                .build();
    }

}
