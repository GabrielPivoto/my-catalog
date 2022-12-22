package br.com.inatel.MyCatalog.model.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowForm {

    @NotEmpty
    private String title;
    @Min(0) @Max(10)
    private double personalScore;

}
