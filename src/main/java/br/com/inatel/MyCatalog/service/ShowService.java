package br.com.inatel.MyCatalog.service;

import br.com.inatel.MyCatalog.adapter.Adapter;
import br.com.inatel.MyCatalog.exception.IncompatibleTypeException;
import br.com.inatel.MyCatalog.exception.ShowAlreadyRegisteredException;
import br.com.inatel.MyCatalog.exception.ShowNotFoundException;
import br.com.inatel.MyCatalog.mapper.Mapper;
import br.com.inatel.MyCatalog.model.dto.ShowDto;
import br.com.inatel.MyCatalog.model.entity.TvShow;
import br.com.inatel.MyCatalog.model.form.ShowForm;
import br.com.inatel.MyCatalog.model.rest.Show;
import br.com.inatel.MyCatalog.repository.ShowRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ShowService {

    private ShowRepository showRepository;
    private Adapter adapter;

    public ResponseEntity<ShowDto> addNewShow(ShowForm showForm){
        String title = showForm.getTitle().toLowerCase().replace(" ","+");
        Show show = adapter.getShowByTitle(title);
        Optional<TvShow> optional = showRepository.findByTitle(showForm.getTitle());
        if(show.getTitle() == null)
            throw new ShowNotFoundException("The show " + showForm.getTitle() + " could not be found." +
                    " Check if the name was written correctly.");
        else if(optional.isPresent())
            throw new ShowAlreadyRegisteredException("The show " + showForm.getTitle() + " is already registered." +
                    " Feel free to add another one.");
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

    public ResponseEntity<?> findShowByTitle(Optional<String> title){
        if(title.isEmpty()){
            List<TvShow> tvShows = showRepository.findAll();
            return ResponseEntity.ok().body(tvShows.stream().map(Mapper::convertEntityToSimpleDto).collect(Collectors.toList()));
        }else{
            Optional<TvShow> optional = showRepository.findByTitle(title.get());
            if(optional.isEmpty())
                throw new ShowNotFoundException("The show " + title.get() + " could not be found." +
                        " Check if the name was written correctly.");
            return ResponseEntity.ok().body(Mapper.convertEntityToDto(optional.get()));
        }
    }

    public ResponseEntity<List<ShowDto>> findAllShowsWithDetails(Optional<String> type){
        List<TvShow> tvShows;
        if(type.isPresent())
            tvShows = showRepository.findAllByType(type.get());
        else
            tvShows = showRepository.findAll();
        return ResponseEntity.ok().body(tvShows.stream().map(Mapper::convertEntityToDto).collect(Collectors.toList()));
    }


    public ResponseEntity<List<ShowDto>> findAllShowsByType(String type){
        List<TvShow> tvShows = showRepository.findAllByType(type);
        return ResponseEntity.ok().body(tvShows.stream().map(Mapper::convertEntityToDto).collect(Collectors.toList()));
    }

    public ResponseEntity<?> findShows(Optional<String> title, Optional<String> type){
        List<TvShow> tvShows;
        if(title.isPresent()){
            Optional<TvShow> optional = showRepository.findByTitle(title.get());
            if(optional.isEmpty())
                throw new ShowNotFoundException("The show " + title.get() + " could not be found." +
                        " Check if the name was written correctly.");
            else if(type.isPresent()){
                if(!optional.get().getType().equals(type.get()))
                    throw new IncompatibleTypeException("The show " + optional.get().getTitle() + " is not a " +
                            type.get());
            }
            return ResponseEntity.ok().body(Mapper.convertEntityToDto(optional.get()));
        }else if(type.isPresent()){
            tvShows = showRepository.findAllByType(type.get());
            return ResponseEntity.ok().body(tvShows.stream()
                    .map(Mapper::convertEntityToDto).collect(Collectors.toList()));
        }else{
            tvShows = showRepository.findAll();
            return ResponseEntity.ok().body(tvShows.stream()
                    .map(Mapper::convertEntityToSimpleDto).collect(Collectors.toList()));
        }
    }

    public ResponseEntity<ShowDto> deleteShow(String title){
        Optional<TvShow> optional = showRepository.findByTitle(title);
        if(optional.isPresent()){
            showRepository.delete(optional.get());
            return ResponseEntity.noContent().build();
        }
        throw new ShowNotFoundException("The show " + title + " could not be found." +
                " Check if the name was written correctly.");
    }


}
