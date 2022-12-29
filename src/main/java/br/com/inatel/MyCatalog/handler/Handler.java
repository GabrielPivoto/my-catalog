package br.com.inatel.MyCatalog.handler;

import br.com.inatel.MyCatalog.exception.ShowAlreadyRegisteredException;
import br.com.inatel.MyCatalog.exception.ShowNotFoundException;
import br.com.inatel.MyCatalog.model.dto.ErrorDto;
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

/**
 * This class handles all exceptions that could be thrown.
 *
 * @author Gabriel Pivoto
 * @version JDK 1.7
 * @since 1.0
 */
@RestControllerAdvice
public class Handler {

    private MessageSource messageSource;

    @Value("${container.name}")
    private String instance;

    public Handler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ShowNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorDto generateException(ShowNotFoundException e) {
        return ErrorDto.builder()
                .type("ShowNotFoundException")
                .title("Show not found or doesn't exist.")
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .detail(e.getMessage())
                .instance(instance)
                .build();
    }

    @ExceptionHandler(ShowAlreadyRegisteredException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDto generateException(ShowAlreadyRegisteredException e) {
        return ErrorDto.builder()
                .type("ShowAlreadyRegisteredException")
                .title("Show already registered.")
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .detail(e.getMessage())
                .instance(instance)
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDto generateException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
        return ErrorDto.builder()
                .type("MethodArgumentNotValidException")
                .title("Invalid personal score")
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .detail("Personal score " + message)
                .instance(instance)
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDto generateException(HttpMessageNotReadableException e) {
        return ErrorDto.builder()
                .type("HttpMessageNotReadableException")
                .title("Invalid personal score")
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .detail("Personal score must be a double.")
                .instance(instance)
                .build();
    }

}
