package br.com.inatel.MyCatalog.definitions;

import br.com.inatel.MyCatalog.exception.ShowAlreadyRegisteredException;
import br.com.inatel.MyCatalog.exception.ShowNotFoundException;
import br.com.inatel.MyCatalog.model.dto.ShowDto;
import br.com.inatel.MyCatalog.model.form.ShowForm;
import br.com.inatel.MyCatalog.model.form.ShowFormTest;
import br.com.inatel.MyCatalog.model.rest.Show;
import br.com.inatel.MyCatalog.service.ShowService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Test definitions of the feature files
 *
 * @author Gabriel Pivoto
 * @version JDK 1.7
 * @since 1.0
 */
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringBootCucumberTestDefinitions {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private ShowService showService;
    private ShowForm form = new ShowForm();
    private ShowFormTest formTest = new ShowFormTest();
    private String result;
    private ShowDto showDto1;
    private ShowDto showDto2;
    private ShowDto showDto3;
    private ShowDto showDto4;
    private String validTitle1;
    private String validTitle2;
    private String validTitle3;
    private String validTitle4;

    @Then("should throw {string}")
    public void should_throw(String exception) {
        verifyString(exception);
    }

    @Given("{int} is provided as personal score")
    public void is_provided_as_personal_score(Integer personalScore) {
        form.setPersonalScore(personalScore);
    }

    @Given("{string} is provided as personal score")
    public void is_provided_as_personal_score(String personalScore) {
        formTest.setPersonalScore(personalScore);
    }

    @When("post a new show with the {string}")
    public void post_a_new_show_with_the(String type) {
        if (type.equals("form")) {
            result = webTestClient.post().uri("/show")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(form)
                    .exchange()
                    .expectBody(Show.class)
                    .returnResult()
                    .toString();
        } else {
            result = webTestClient.post().uri("/show")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(formTest)
                    .exchange()
                    .expectBody(Show.class)
                    .returnResult()
                    .toString();
        }
    }

    @And("the status should be {string}")
    public void the_status_should_be(String status) {
        verifyString(status);
    }

    @Given("{string} is provided as title")
    public void is_provided_as_title(String title) {
        form.setTitle(title);
    }

    @Given("{string} is already registered")
    public void is_already_registered(String title) {
        form = ShowForm.builder()
                .title(title)
                .personalScore(10)
                .build();
        when(showService.addNewShow(form)).thenThrow(ShowAlreadyRegisteredException.class);
    }

    @Given("{string} is an invalid title")
    public void is_an_invalid_title(String title) {
        form = ShowForm.builder()
                .title(title)
                .personalScore(10)
                .build();
        when(showService.addNewShow(form)).thenThrow(ShowNotFoundException.class);
    }

    @Given("no type provided")
    public void no_type_provided() {
        validTitle1 = "Game of Thrones";
        validTitle2 = "Breaking Bad";
        validTitle3 = "Avengers";
        validTitle4 = "Forrest Gump";

        showDto1 = ShowDto.builder()
                .id(1)
                .title(validTitle1)
                .type("series")
                .personalScore(9.7)
                .build();


        showDto2 = ShowDto.builder()
                .id(2)
                .title(validTitle2)
                .type("series")
                .personalScore(8)
                .build();

        showDto3 = ShowDto.builder()
                .id(3)
                .title(validTitle3)
                .type("movie")
                .personalScore(7)
                .build();

        showDto4 = ShowDto.builder()
                .id(4)
                .title(validTitle4)
                .type("movie")
                .personalScore(10)
                .build();
        when(showService.findShows(Optional.empty())).thenReturn(List.of(showDto1, showDto2, showDto3, showDto4));
    }

    @When("get shows")
    public void getShows() {
        result = webTestClient.get().uri("/show")
                .exchange()
                .expectBody(ShowDto[].class)
                .returnResult()
                .toString();
    }

    @Then("should return list of shows")
    public void shouldReturnListOfShows() {
        assertTrue(result.contains(validTitle1));
        assertTrue(result.contains(validTitle2));
        assertTrue(result.contains(validTitle3));
        assertTrue(result.contains(validTitle4));
    }

    @And("the detail should be {string}")
    public void the_detail_should_be(String detail) {
        verifyString(detail);
    }

    public void verifyString(String string) {
        assertTrue(result.contains(string));
    }

    @And("the title should be {string}")
    public void theTitleShouldBe(String title) {
        verifyString(title);
    }

    @Given("{string} is not yet registered")
    public void is_not_yet_registered(String title) {
        ShowDto dto = ShowDto.builder()
                .title("Pacific Rim")
                .rated("PG-13")
                .personalScore(10)
                .director("Guillermo del Toro")
                .actors("Guillermo del Toro")
                .type("movie")
                .build();
        when(showService.addNewShow(form)).thenReturn(dto);
    }

    @And("the director should be {string}")
    public void the_director_should_be(String director) {
        verifyString(director);
    }

    @And("the type should be {string}")
    public void the_type_should_be(String type) {
        verifyString(type);
    }

    @Given("type {string} is provided")
    public void typeIsProvided(String type) {
        validTitle1 = "Game of Thrones";
        validTitle2 = "Breaking Bad";
        validTitle3 = "Avengers";
        validTitle4 = "Forrest Gump";

        showDto1 = ShowDto.builder()
                .id(1)
                .title(validTitle1)
                .type("series")
                .personalScore(9.7)
                .build();


        showDto2 = ShowDto.builder()
                .id(2)
                .title(validTitle2)
                .type("series")
                .personalScore(8)
                .build();

        showDto3 = ShowDto.builder()
                .id(3)
                .title(validTitle3)
                .type("movie")
                .personalScore(7)
                .build();

        showDto4 = ShowDto.builder()
                .id(4)
                .title(validTitle4)
                .type("movie")
                .personalScore(10)
                .build();
        switch (type) {
            case "movie" -> when(showService.findShows(Optional.of("movie"))).thenReturn(List.of(showDto3, showDto4));
            case "series" -> when(showService.findShows(Optional.of("series"))).thenReturn(List.of(showDto1, showDto2));
        }
    }

    @When("get {string}")
    public void get(String type) {
        result = webTestClient.get().uri("/show?type=" + type)
                .exchange()
                .expectBody(ShowDto[].class)
                .returnResult()
                .toString();
    }

    @Then("should return list of {string}")
    public void shouldReturnListOf(String type) {
        verifyString(type);
        if (type.equals("movie"))
            assertFalse(result.contains("series"));
        else
            assertFalse(result.contains("movie"));
    }

    @When("get show by id {int}")
    public void getShowById(int id) {
        result = webTestClient.get().uri("/show/" + id)
                .exchange()
                .expectBody(ShowDto.class)
                .returnResult()
                .toString();
    }

    @Given("id {int} is {string}")
    public void idIs(int id, String valid) {
        validTitle1 = "Game of Thrones";

        showDto1 = ShowDto.builder()
                .id(1)
                .title(validTitle1)
                .actors("Peter Dinklage")
                .type("series")
                .director("David Benioff, D.B. Weiss")
                .personalScore(9.7)
                .build();

        switch (valid) {
            case "valid" -> when(showService.findShow(id)).thenReturn(showDto1);
            case "invalid" -> when(showService.findShow(id)).thenThrow(ShowNotFoundException.class);
        }

    }
}
