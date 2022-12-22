package br.com.inatel.MyCatalog.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TvShow {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull @Min(0) @Max(10)
    private double personalScore;
    @NotNull
    private String title;
    @NotNull
    private String rated;
    @NotNull
    private String released;
    @NotNull
    private String genre;
    @NotNull
    private String director;
    @NotNull
    private String writer;
    @NotNull
    private String actors;
    @NotNull
    private String plot;
    @NotNull
    private String imdbRating;
    @NotNull
    private String type;

}
