package br.com.inatel.MyCatalog.service;

import br.com.inatel.MyCatalog.adapter.Adapter;
import br.com.inatel.MyCatalog.exception.ShowAlreadyRegisteredException;
import br.com.inatel.MyCatalog.exception.ShowNotFoundException;
import br.com.inatel.MyCatalog.mapper.Mapper;
import br.com.inatel.MyCatalog.model.dto.ShowDto;
import br.com.inatel.MyCatalog.model.entity.TvShow;
import br.com.inatel.MyCatalog.model.form.ShowForm;
import br.com.inatel.MyCatalog.model.rest.Show;
import br.com.inatel.MyCatalog.repository.ShowRepository;
import br.com.inatel.MyCatalog.message.AlreadyRegisteredMessage;
import br.com.inatel.MyCatalog.message.DeletedMessage;
import br.com.inatel.MyCatalog.message.NotFoundMessage;
import br.com.inatel.MyCatalog.message.SavedMesssage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class. Business logic implemented here.
 *
 * @author Gabriel Pivoto
 * @version JDK 1.7
 * @since 1.0
 */
@Service
@AllArgsConstructor
@Slf4j
public class ShowService {

    private ShowRepository showRepository;
    private Adapter adapter;

    public ShowDto addNewShow(ShowForm showForm) {
        Show show = adapter.getShowByTitle(showForm.getTitle());
        Optional<TvShow> optional = showRepository.findByTitle(show.getTitle());
        if (show.getTitle() == null) {
            log.error(new NotFoundMessage().showMessage(showForm.getTitle()));
            throw new ShowNotFoundException(new NotFoundMessage().showMessage(showForm.getTitle()));
        } else if (optional.isPresent()) {
            log.error(new AlreadyRegisteredMessage().showMessage(optional.get().getTitle()));
            throw new ShowAlreadyRegisteredException(new AlreadyRegisteredMessage()
                    .showMessage(optional.get().getTitle()));
        } else {
            TvShow tvShow = TvShow.builder()
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
                    .type(show.getType())
                    .build();
            showRepository.save(tvShow);
            log.info(new SavedMesssage().showMessage(tvShow.getTitle()));
            return Mapper.convertEntityToDto(tvShow);
        }
    }

    public List<ShowDto> findShows(Optional<String> type) {
        List<TvShow> tvShows;
        if (type.isPresent()) {
            log.info("Type " + type.get());
            tvShows = showRepository.findAllByType(type.get());
            return tvShows.stream().map(Mapper::convertEntityToDto).toList();
        }
        log.info("All shows.");
        tvShows = showRepository.findAll();
        return tvShows.stream().map(Mapper::convertEntityToDto).toList();
    }

    public ShowDto findShow(int id) {
        Optional<TvShow> optional = showRepository.findById(id);
        if (optional.isEmpty()) {
            log.error(new NotFoundMessage().showMessage(String.valueOf(id)));
            throw new ShowNotFoundException(new NotFoundMessage().showMessage(String.valueOf(id)));
        }
        return Mapper.convertEntityToDto(optional.get());
    }

    public ShowDto updateShow(ShowForm showForm) {
        Optional<TvShow> optional = showRepository.findByTitle(showForm.getTitle());
        if (optional.isEmpty()) {
            log.error(new NotFoundMessage().showMessage(showForm.getTitle()));
            throw new ShowNotFoundException(new NotFoundMessage().showMessage(showForm.getTitle()));
        }
        TvShow tvShow = optional.get();
        tvShow.setPersonalScore(showForm.getPersonalScore());
        log.info("The show " + optional.get().getTitle() + " has been updated");
        return Mapper.convertEntityToDto(showRepository.save(tvShow));
    }

    public void deleteShow(int id) {
        Optional<TvShow> optional = showRepository.findById(id);
        if (optional.isPresent()) {
            log.info(new DeletedMessage().showMessage(optional.get().getTitle()));
            showRepository.deleteById(id);
        } else {
            log.error(new NotFoundMessage().showMessage(String.valueOf(id)));
            throw new ShowNotFoundException(new NotFoundMessage().showMessage(String.valueOf(id)));
        }
    }

}
