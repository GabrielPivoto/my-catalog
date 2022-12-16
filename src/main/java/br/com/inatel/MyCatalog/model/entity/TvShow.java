package br.com.inatel.MyCatalog.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
