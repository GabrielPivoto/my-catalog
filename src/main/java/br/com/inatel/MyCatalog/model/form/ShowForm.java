package br.com.inatel.MyCatalog.model.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * This class represents the information the user needs to send to add a bew show
 *
 * @author Gabriel Pivoto
 * @version JDK 1.7
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowForm {

    @NotEmpty
    private String title;
    @Min(0)
    @Max(10)
    private double personalScore;

}
