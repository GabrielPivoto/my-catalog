package br.com.inatel.MyCatalog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowSimpleDto {

    private String title;
    private double personalScore;
    private String rated;
    private String released;
    private String genre;
    private String director;
    private String writer;
    private String actors;
    private String plot;
    private String imdbRating;
    private String type;

}
