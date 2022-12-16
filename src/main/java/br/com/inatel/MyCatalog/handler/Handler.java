package br.com.inatel.MyCatalog.handler;

import br.com.inatel.MyCatalog.exception.IncompatibleTypeException;
import br.com.inatel.MyCatalog.exception.ShowAlreadyRegisteredException;
import br.com.inatel.MyCatalog.exception.ShowNotFoundException;
import br.com.inatel.MyCatalog.model.dto.ErrorDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Handler {

    private MessageSource messageSource;

    @Value("${container.name}")
    private String instance;

    public Handler(MessageSource messageSource){
        this.messageSource = messageSource;
    }



    @ExceptionHandler(ShowNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorDto generateException(ShowNotFoundException e){
        return ErrorDto.builder()
                .type("ShowNotFoundException")
                .title("Show not found or doesn't exist.")
                .httpStatus(HttpStatus.NOT_FOUND)
                .detail(e.getMessage())
                .instance(instance)
                .build();
    }

    @ExceptionHandler(ShowAlreadyRegisteredException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorDto generateException(ShowAlreadyRegisteredException e){
        return ErrorDto.builder()
                .type("ShowAlreadyRegisteredException")
                .title("Show already registered.")
                .httpStatus(HttpStatus.CONFLICT)
                .detail(e.getMessage())
                .instance(instance)
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDto generateException(MethodArgumentNotValidException e){
        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
        return ErrorDto.builder()
                .type("MethodArgumentNotValidException")
                .title("Invalid personal score")
                .httpStatus(HttpStatus.BAD_REQUEST)
                .detail("Personal score " + message)
                .instance(instance)
                .build();
    }

    @ExceptionHandler(IncompatibleTypeException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDto generateException(IncompatibleTypeException e){
        return ErrorDto.builder()
                .type("IncompatibleTypeException")
                .title("Incompatible types")
                .httpStatus(HttpStatus.BAD_REQUEST)
                .detail(e.getMessage())
                .instance(instance)
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDto generateException(HttpMessageNotReadableException e){
        return ErrorDto.builder()
                .type("HttpMessageNotReadableException")
                .title("Invalid personal score")
                .httpStatus(HttpStatus.BAD_REQUEST)
                .detail("Personal score must be a double.")
                .instance(instance)
                .build();
    }

}
