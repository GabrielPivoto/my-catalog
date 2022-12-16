package br.com.inatel.MyCatalog.mapper;

import br.com.inatel.MyCatalog.model.dto.ShowDto;
import br.com.inatel.MyCatalog.model.dto.ShowSimpleDto;
import br.com.inatel.MyCatalog.model.entity.TvShow;

public class Mapper {

    public static ShowDto convertEntityToDto(TvShow tvShow){
        return ShowDto.builder()
                .actors(tvShow.getActors())
                .id(tvShow.getId())
                .personalScore(tvShow.getPersonalScore())
                .director(tvShow.getDirector())
                .title(tvShow.getTitle())
                .genre(tvShow.getGenre())
                .imdbRating(tvShow.getImdbRating())
                .plot(tvShow.getPlot())
                .rated(tvShow.getRated())
                .released(tvShow.getReleased())
                .type(tvShow.getType())
                .writer(tvShow.getWriter())
                .build();
    }

    public static ShowSimpleDto convertEntityToSimpleDto(TvShow tvShow){
        return ShowSimpleDto.builder()
                .title(tvShow.getTitle())
                .id(tvShow.getId())
                .personalScore(tvShow.getPersonalScore())
                .build();
    }

}
