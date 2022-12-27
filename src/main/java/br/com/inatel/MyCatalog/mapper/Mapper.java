package br.com.inatel.MyCatalog.mapper;

import br.com.inatel.MyCatalog.model.dto.ShowDto;
import br.com.inatel.MyCatalog.model.entity.TvShow;

/**
 * This class maps Entity to Dto
 *
 * @author Gabriel Pivoto
 * @version JDK 1.7
 * @since 1.0
 */
public class Mapper {

    public static ShowDto convertEntityToDto(TvShow tvShow) {
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

}
