package br.com.inatel.MyCatalog.controller;

import br.com.inatel.MyCatalog.exception.ShowAlreadyRegisteredException;
import br.com.inatel.MyCatalog.exception.ShowNotFoundException;
import br.com.inatel.MyCatalog.model.dto.ShowDto;
import br.com.inatel.MyCatalog.model.form.ShowForm;
import br.com.inatel.MyCatalog.model.form.ShowFormTest;
import br.com.inatel.MyCatalog.model.rest.Show;
import br.com.inatel.MyCatalog.service.ShowService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ShowController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private ShowService showService;

    private JacksonTester<ShowDto> jsonShow;
    private JacksonTester<List<ShowDto>> jsonShows;
    private JacksonTester<ShowForm> jsonForm;
    private JacksonTester<ShowFormTest> jsonFormTest;

    private ShowDto showDto1;
    private ShowDto showDtoUpdated;
    private ShowDto showDto2;
    private ShowDto showDto3;
    private ShowDto showDto4;
    private List<ShowDto> showDtos;
    private ShowForm showForm1;
    private ShowForm showForm2;
    private ShowForm showForm3;
    private ShowForm showForm4;
    private ShowFormTest showFormTest1;
    private ShowForm invalidShowForm1;
    private ShowForm invalidShowForm2;
    private String validTitle1;
    private String validTitle2;
    private String validTitle3;
    private String validTitle4;
    private String invalidTitle;

    @BeforeEach
    public void init(){

        JacksonTester.initFields(this, new ObjectMapper());

        validTitle1 = "Game of Thrones";
        validTitle2 = "Breaking Bad";
        validTitle3 = "Avengers";
        validTitle4 = "Forrest Gump";
        invalidTitle = "House of the Horse";

        showDto1 = ShowDto.builder()
                .id(1)
                .title(validTitle1)
                .type("series")
                .personalScore(9.7)
                .build();

        showDtoUpdated = ShowDto.builder()
                .id(1)
                .title(validTitle1)
                .type("series")
                .personalScore(10)
                .build();

        showDto2 = ShowDto.builder()
                .id(2)
                .title("Breaking Bad")
                .type("series")
                .personalScore(8)
                .build();

        showDto3 = ShowDto.builder()
                .id(3)
                .title("The Avengers")
                .type("movie")
                .personalScore(7)
                .build();

        showDto4 = ShowDto.builder()
                .id(4)
                .title("Forrest Gump")
                .type("movie")
                .personalScore(10)
                .build();

        showForm1 = ShowForm.builder()
                .title(validTitle1)
                .personalScore(10)
                .build();

        showForm2 = ShowForm.builder()
                .title(validTitle2)
                .personalScore(8)
                .build();

        showForm3 = ShowForm.builder()
                .title(validTitle3)
                .personalScore(8.5)
                .build();

        showForm4 = ShowForm.builder()
                .title(validTitle4)
                .personalScore(10)
                .build();

        invalidShowForm1 = ShowForm.builder()
                .title(invalidTitle)
                .personalScore(9.7)
                .build();

        showFormTest1 = ShowFormTest.builder()
                .title(validTitle1)
                .personalScore("a random score")
                .build();

       invalidShowForm2 = ShowForm.builder()
                .title(invalidTitle)
                .personalScore(12)
                .build();

        showDtos = new ArrayList<>();
        showDtos.add(showDto1);
        showDtos.add(showDto2);
        showDtos.add(showDto3);
        showDtos.add(showDto4);

    }

    @Test
    public void givenTypeNotProvided_whenGetAllShows_shouldReturnShowsListAndStatus200Ok() throws Exception {
        when(showService.findShows(Optional.empty())).thenReturn(List.of(showDto1,showDto2,showDto3,showDto4));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/show")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonShows.write(showDtos).getJson());
    }

    @Test
    public void givenTypeMovie_whenGetShowsByTypeMovie_shouldReturnOnlyMoviesAndStatus200Ok() throws Exception {
        when(showService.findShows(Optional.of("movie"))).thenReturn(List.of(showDto3,showDto4));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/show?type=movie")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonShows.write(showDtos.stream().filter(s -> s.getType().equals("movie")).toList()).getJson());
    }

    @Test
    public void givenTypeSeries_whenGetShowsByTypeSeries_shouldReturnOnlySeriesAndStatus200Ok() throws Exception {
        when(showService.findShows(Optional.of("series"))).thenReturn(List.of(showDto1,showDto2));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/show?type=series")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonShows.write(showDtos.stream().filter(s -> s.getType().equals("series")).toList()).getJson());
    }

    @Test
    public void givenInvalidType_whenGetShowsByType_shouldReturnEmptyListAndStatus200Ok() throws Exception {
        when(showService.findShows(Optional.of("invalid"))).thenReturn(List.of());

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/show?type=invalid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonShows.write(List.of()).getJson());
    }

    @Test
    public void givenValidId_whenGetShowById_shouldReturnShowDtoAndStatusOk() throws Exception {
        when(showService.findShow(1)).thenReturn(showDto1);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/show/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonShow.write(showDto1).getJson());
    }

    @Test
    public void givenInvalidId_whenGetShowById_shouldThrowShowNotFoundExceptionAndStatus404NotFound() throws Exception {
        when(showService.findShow(1)).thenThrow(ShowNotFoundException.class);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/show/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertTrue(response.getContentAsString().contains("Show not found or doesn't exist."));
    }

    @Test
    public void givenValidTitle_whenPostShow_shouldReturnShowDtoAndStatus201Created() throws Exception {
        when(showService.addNewShow(showForm1)).thenReturn(showDto1);

        MockHttpServletResponse response = mockMvc.perform(
                post("/show").contentType(MediaType.APPLICATION_JSON).content(
                        jsonForm.write(showForm1).getJson())).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonShow.write(showDto1).getJson());
    }

    @Test
    public void givenInvalidTitle_whenPostShow_shouldThrowShowNotFoundExceptionAndStatus404NotFound() throws Exception {
        when(showService.addNewShow(showForm1)).thenThrow(ShowNotFoundException.class);

        MockHttpServletResponse response = mockMvc.perform(
                post("/show").contentType(MediaType.APPLICATION_JSON).content(
                        jsonForm.write(showForm1).getJson())).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertTrue(response.getContentAsString().contains("Show not found or doesn't exist."));
    }

    @Test
    public void givenRegisteredTitle_whenPostShow_shouldThrowShowAlreadyRegisteredExceptionAndStatus400BadRequest()
            throws Exception {
        when(showService.addNewShow(showForm1)).thenThrow(ShowAlreadyRegisteredException.class);

        MockHttpServletResponse response = mockMvc.perform(
                post("/show").contentType(MediaType.APPLICATION_JSON).content(
                        jsonForm.write(showForm1).getJson())).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertTrue(response.getContentAsString().contains("Show already registered."));
    }

    @Test
    public void givenValidTitle_whenPatchShow_shouldReturn200OkAndUpdateTheShow() throws Exception{
        when(showService.updateShow(showForm1)).thenReturn(showDtoUpdated);

        MockHttpServletResponse response = mockMvc.perform(
                patch("/show").contentType(MediaType.APPLICATION_JSON).content(
                        jsonForm.write(showForm1).getJson())).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonShow.write(showDtoUpdated).getJson());
    }

    @Test
    public void givenInvalidTitle_whenPatchShow_shouldThrowShowNotFoundException() throws Exception{
        when(showService.updateShow(invalidShowForm1)).thenThrow(ShowNotFoundException.class);

        MockHttpServletResponse response = mockMvc.perform(
                patch("/show").contentType(MediaType.APPLICATION_JSON).content(
                        jsonForm.write(invalidShowForm1).getJson())).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertTrue(response.getContentAsString().contains("Show not found or doesn't exist."));
    }

    @Test
    public void givenValidId_whenDeleteShow_shouldDeleteTheShowAndReturnStatus204NoContent() throws Exception{
        doNothing().when(showService).deleteShow(1);

        MockHttpServletResponse response = mockMvc.perform(
                delete("/show/1").contentType(MediaType.APPLICATION_JSON).content(
                        jsonForm.write(invalidShowForm1).getJson())).andReturn().getResponse();


        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void givenInvalidId_whenDeleteShow_shouldThrowShowNotFoundException() throws Exception{
        willThrow(ShowNotFoundException.class).given(showService).deleteShow(1);

        MockHttpServletResponse response = mockMvc.perform(
                delete("/show/1").contentType(MediaType.APPLICATION_JSON).content(
                        jsonForm.write(invalidShowForm1).getJson())).andReturn().getResponse();


        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertTrue(response.getContentAsString().contains("Show not found or doesn't exist."));
    }

    @Test
    public void givenStringPersonalScore_whenPostShow_shouldThrowHttpMessageNotReadableException(){
        ShowFormTest form = ShowFormTest.builder()
                .title("Game of Thrones")
                .personalScore("personal score")
                .build();

        String result = webTestClient.post().uri("/show")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(form)
                .exchange()
                .expectStatus().isBadRequest().expectBody(Show.class)
                .returnResult()
                .toString();

        assertTrue(result.contains("HttpMessageNotReadableException"));
        assertTrue(result.contains("Personal score must be a double"));
    }

    @Test
    public void givenPersonalScoreHigherThan10_whenPostShow_shouldThrowMethodArgumentNotValidException(){
        ShowForm form = ShowForm.builder()
                .title("Game of Thrones")
                .personalScore(11)
                .build();

        String result = webTestClient.post().uri("/show")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(form)
                .exchange()
                .expectStatus().isBadRequest().expectBody(Show.class)
                .returnResult()
                .toString();

        assertTrue(result.contains("MethodArgumentNotValidException"));
        assertTrue(result.contains("Personal score must be less than or equal to 10"));
    }

    @Test
    public void givenPersonalScoreLowerThan0_whenPostShow_shouldThrowMethodArgumentNotValidException(){
        ShowForm form = ShowForm.builder()
                .title("Game of Thrones")
                .personalScore(-1)
                .build();

        String result = webTestClient.post().uri("/show")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(form)
                .exchange()
                .expectStatus().isBadRequest().expectBody(Show.class)
                .returnResult()
                .toString();

        assertTrue(result.contains("MethodArgumentNotValidException"));
        assertTrue(result.contains("Personal score must be greater than or equal to 0"));
    }

}