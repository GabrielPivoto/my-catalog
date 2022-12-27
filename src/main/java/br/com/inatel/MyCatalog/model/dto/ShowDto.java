package br.com.inatel.MyCatalog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto representing the information the user will see about the show
 *
 * @author Gabriel Pivoto
 * @version JDK 1.7
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowDto {

    private String title;
    private int id;
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
