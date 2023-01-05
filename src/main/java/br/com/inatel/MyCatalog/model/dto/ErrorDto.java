package br.com.inatel.MyCatalog.model.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Dto shown when an exception is handled.
 *
 * @author Gabriel Pivoto
 * @version JDK 1.7
 * @since 1.0
 */
@Data
@Builder
public class ErrorDto {

    private String type;
    private String title;
    private int httpStatus;
    private String detail;
    private String instance;

}
