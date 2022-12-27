package br.com.inatel.MyCatalog.definitions;

import br.com.inatel.MyCatalog.model.form.ShowForm;
import br.com.inatel.MyCatalog.model.rest.Show;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertTrue;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringBootCucumberTestDefinitions {

    @Autowired
    private WebTestClient webTestClient;
    private ShowForm form;
    private String result;

    @Given("{int} is provided as personal score")
    public void is_provided_as_personal_score(Integer int1) {
        form = ShowForm.builder()
                .title("Game of Thrones")
                .personalScore(11)
                .build();
    }
    @When("post a new show")
    public void post_a_new_show() {
        result = webTestClient.post().uri("/show")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(form)
                .exchange()
                .expectStatus().isBadRequest().expectBody(Show.class)
                .returnResult()
                .toString();
    }
    @Then("should throw MethodArgumentNotValidException")
    public void should_throw_method_argument_not_valid_exception() {
        assertTrue(result.contains("MethodArgumentNotValidException"));
        assertTrue(result.contains("Personal score must be less than or equal to 10"));
    }

}
