package br.com.inatel.MyCatalog.service;

import br.com.inatel.MyCatalog.adapter.OmdbAdapter;
import br.com.inatel.MyCatalog.exception.ShowAlreadyRegisteredException;
import br.com.inatel.MyCatalog.exception.ShowNotFoundException;
import br.com.inatel.MyCatalog.model.dto.ShowDto;
import br.com.inatel.MyCatalog.model.entity.TvShow;
import br.com.inatel.MyCatalog.model.form.ShowForm;
import br.com.inatel.MyCatalog.model.rest.Show;
import br.com.inatel.MyCatalog.repository.ShowRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Service tests
 *
 * @author Gabriel Pivoto
 * @version JDK 1.7
 * @since 1.0
 */
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ShowServiceTest {

    @Mock
    private ShowRepository showRepository;
    @Mock
    private OmdbAdapter adapter;
    @InjectMocks
    private ShowService showService = new ShowService(showRepository, adapter);
    private TvShow validTvShow1;
    private Show validShow1;
    private Show invalidShow1;
    private String validTitle1;
    private String validTitle2;
    private String validTitle3;
    private String validTitle4;
    private String invalidTitle;
    private ShowForm showForm1;
    private ShowForm invalidShowForm;
    List<TvShow> tvShowList = new ArrayList<>();
    List<TvShow> moviesList = new ArrayList<>();
    List<TvShow> seriesList = new ArrayList<>();


    @Before
    public void init() {

        validTitle1 = "Game of Thrones";
        validTitle2 = "Breaking Bad";
        validTitle3 = "Avengers";
        validTitle4 = "Forrest Gump";
        invalidTitle = "House of the Horse";

        validTvShow1 = TvShow.builder()
                .id(1)
                .title("Game of Thrones")
                .rated("random")
                .personalScore(9.7)
                .rated("random")
                .director("director")
                .actors("actors")
                .imdbRating("8.4")
                .writer("writer")
                .type("series")
                .plot("plot")
                .released("date")
                .build();

        validShow1 = Show.builder()
                .title("Game of Thrones")
                .rated("random")
                .year("year")
                .director("director")
                .actors("actors")
                .imdbRating("8.4")
                .writer("writer")
                .type("series")
                .plot("plot")
                .released("date")
                .genre("genre")
                .build();

        invalidShow1 = Show.builder()
                .title(null)
                .rated("random")
                .year("year")
                .director("director")
                .actors("actors")
                .imdbRating("8.4")
                .writer("writer")
                .type("series")
                .plot("plot")
                .released("date")
                .genre("genre")
                .build();

        TvShow validTvShow2 = TvShow.builder()
                .id(2)
                .title("Breaking Bad")
                .rated("random")
                .personalScore(8)
                .rated("random")
                .director("director")
                .actors("actors")
                .imdbRating("8.4")
                .writer("writer")
                .type("series")
                .plot("plot")
                .released("date")
                .build();

        TvShow validTvShow3 = TvShow.builder()
                .id(3)
                .title("Avengers")
                .rated("random")
                .personalScore(8.5)
                .rated("random")
                .director("director")
                .actors("actors")
                .imdbRating("8.4")
                .writer("writer")
                .type("movie")
                .plot("plot")
                .released("date")
                .build();

        TvShow validTvShow4 = TvShow.builder()
                .id(4)
                .title("Forrest Gump")
                .rated("random")
                .personalScore(10)
                .rated("random")
                .director("director")
                .actors("actors")
                .imdbRating("8.4")
                .writer("writer")
                .type("movie")
                .plot("plot")
                .released("date")
                .build();

        showForm1 = ShowForm.builder()
                .title(validTitle1)
                .personalScore(10)
                .build();

        invalidShowForm = ShowForm.builder()
                .title(invalidTitle)
                .personalScore(9.7)
                .build();

        tvShowList.add(validTvShow1);
        tvShowList.add(validTvShow2);
        tvShowList.add(validTvShow3);
        tvShowList.add(validTvShow4);
        seriesList.add(validTvShow1);
        seriesList.add(validTvShow2);
        moviesList.add(validTvShow3);
        moviesList.add(validTvShow4);

    }

    @Test
    public void givenValidTitle_whenPostShow_shouldReturnShowDto() {
        when(adapter.getShowByTitle(validTitle1)).thenReturn(validShow1);
        when(showRepository.findByTitle(validTitle1)).thenReturn(Optional.empty());

        ShowDto showDto = showService.addNewShow(showForm1);

        assertEquals(validTitle1, showDto.getTitle());
    }

    @Test
    public void givenInvalidTitle_whenPostShow_shouldReturnShowNotFoundException() {
        when(adapter.getShowByTitle(invalidTitle)).thenReturn(invalidShow1);

        Throwable throwable = catchThrowable(() -> showService.addNewShow(invalidShowForm));

        assertThat(throwable)
                .isInstanceOf(ShowNotFoundException.class)
                .hasMessageContaining("The title " + invalidShowForm.getTitle() + " could not be found. " +
                        "Check if it is valid");
    }

    @Test
    public void givenAlreadyRegisteredTitle_whenPostShow_shouldReturnShowAlreadyRegisteredException() {
        when(adapter.getShowByTitle(validTitle1)).thenReturn(validShow1);
        when(showRepository.findByTitle(validTitle1)).thenReturn(Optional.of(validTvShow1));

        Throwable throwable = catchThrowable(() -> showService.addNewShow(showForm1));

        assertThat(throwable)
                .isInstanceOf(ShowAlreadyRegisteredException.class)
                .hasMessageContaining("The title " + showForm1.getTitle() + " is already registered." +
                        " Try adding another one");
    }

    @Test
    public void givenTypeNotProvided_whenGetAllShows_shouldReturnShowsList() {
        given(showRepository.findAll()).willReturn(tvShowList);

        List<ShowDto> showDtos = showService.findShows(Optional.empty());

        assertEquals(4, showDtos.size());
        assertEquals(validTitle1, showDtos.get(0).getTitle());
        assertEquals(validTitle2, showDtos.get(1).getTitle());
        assertEquals(validTitle3, showDtos.get(2).getTitle());
        assertEquals(validTitle4, showDtos.get(3).getTitle());
    }

    @Test
    public void givenTypeMovie_whenGetShowsByTypeMovie_shouldReturnOnlyMovies() {
        when(showRepository.findAllByType("movie")).thenReturn(moviesList);

        List<ShowDto> showDtos = showService.findShows(Optional.of("movie"));

        assertEquals(2, showDtos.size());
        assertEquals("movie", moviesList.get(0).getType());
        assertEquals("movie", moviesList.get(1).getType());
    }

    @Test
    public void givenTypeSeries_whenGetShowsByTypeSeries_shouldReturnOnlySeries() {
        when(showRepository.findAllByType("series")).thenReturn(seriesList);

        List<ShowDto> showDtos = showService.findShows(Optional.of("series"));

        assertEquals(2, showDtos.size());
        assertEquals("series", seriesList.get(0).getType());
        assertEquals("series", seriesList.get(1).getType());
    }

    @Test
    public void givenInvalidType_whenGetShowsByType_shouldReturnEmptyList() {
        when(showRepository.findAllByType("invalid")).thenReturn(List.of());

        List<ShowDto> showDtos = showService.findShows(Optional.of("invalid"));

        assertEquals(0, showDtos.size());
    }

    @Test
    public void givenValidId_whenGetShowById_shouldReturnShowDto() {
        when(showRepository.findById(1)).thenReturn(Optional.of(validTvShow1));

        ShowDto showDto = showService.findShow(1);

        assertEquals(validTitle1, showDto.getTitle());
        assertEquals(9.7, showDto.getPersonalScore());
    }

    @Test
    public void givenInvalidId_whenGetShowById_shouldThrowShowNotFoundException() {
        when(showRepository.findById(8)).thenReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> showService.findShow(8));

        assertThat(throwable)
                .isInstanceOf(ShowNotFoundException.class)
                .hasMessageContaining("The id 8 could not be found. Check if it is valid");
    }

    @Test
    public void givenValidId_whenDeleteShowById_thenTheShowMustBeDeleted() {
        when(showRepository.findById(1)).thenReturn(Optional.of(validTvShow1));

        showService.deleteShow(1);

        verify(showRepository).deleteById(1);
    }

    @Test
    public void givenInvalidId_whenDeleteShowById_theShouldTrhowShowNotFoundException() {
        when(showRepository.findById(8)).thenReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> showService.deleteShow(8));

        assertThat(throwable)
                .isInstanceOf(ShowNotFoundException.class)
                .hasMessageContaining("The id 8 could not be found. Check if it is valid");
    }

    @Test
    public void givenValidTitle_whenPatchShow_shouldUptadeTheShow() {
        when(showRepository.findById(1)).thenReturn(Optional.of(validTvShow1));
        when(showRepository.findByTitle(validTitle1)).thenReturn(Optional.of(validTvShow1));
        when(showRepository.save(validTvShow1)).thenReturn(validTvShow1);

        ShowDto showBefore = showService.findShow(1);
        assertEquals(9.7, showBefore.getPersonalScore());

        showService.updateShow(showForm1);
        ShowDto showAfter = showService.findShow(1);
        assertEquals(10, showAfter.getPersonalScore());
    }

    @Test
    public void givenInvalidTitle_whenPatchShow_shouldThrowShowNotFoundException() {
        when(showRepository.findByTitle(validTitle1)).thenReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> showService.updateShow(showForm1));

        assertThat(throwable)
                .isInstanceOf(ShowNotFoundException.class)
                .hasMessageContaining("The title " + showForm1.getTitle() + " could not be found." +
                        " Check if it is valid");
    }

}
