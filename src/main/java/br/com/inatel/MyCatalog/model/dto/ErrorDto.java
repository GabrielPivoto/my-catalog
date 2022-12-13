package br.com.inatel.MyCatalog.model.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ErrorDto {

    private String type;
    private String title;
    private HttpStatus httpStatus;
    private String detail;
    private String instance;

}
