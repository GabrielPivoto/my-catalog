package br.com.inatel.MyCatalog.service;

import br.com.inatel.MyCatalog.adapter.Adapter;
import br.com.inatel.MyCatalog.exception.ShowNotFoundException;
import br.com.inatel.MyCatalog.mapper.Mapper;
import br.com.inatel.MyCatalog.model.dto.ShowDto;
import br.com.inatel.MyCatalog.model.form.ShowForm;
import br.com.inatel.MyCatalog.model.entity.TvShow;
import br.com.inatel.MyCatalog.model.rest.Show;
import br.com.inatel.MyCatalog.repository.ShowRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ShowService {

    private ShowRepository showRepository;
    private Adapter adapter;

    public ResponseEntity<ShowDto> addNewShow(ShowForm showForm){
        String title = showForm.getTitle().toLowerCase().replace(" ","+");
        Show show = adapter.getShowByTitle(title);
        if(show.getTitle() == null)
            throw new ShowNotFoundException();
        else{
            return ResponseEntity.created(null).body(Mapper.convertEntityToDto(showRepository.save(TvShow.builder()
                    .personalScore(showForm.getPersonalScore())
                    .title(show.getTitle())
                    .rated(show.getRated())
                    .released(show.getReleased())
                    .genre(show.getGenre())
                    .director(show.getDirector())
                    .writer(show.getWriter())
                    .actors(show.getActors())
                    .plot(show.getPlot())
                    .imdbRating(show.getImdbRating())
                    .type(show.getType()).build())));
        }
    }

    public ResponseEntity<ShowDto> findShowById(String title){
        Optional<TvShow> optional = showRepository.findByTitle(title);
        if(optional.isEmpty())
            throw new ShowNotFoundException();
        else{
            return ResponseEntity.ok().body(Mapper.convertEntityToDto(optional.get()));
        }
    }

}
