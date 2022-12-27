package br.com.inatel.MyCatalog.adapter;

import br.com.inatel.MyCatalog.model.rest.Show;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * This class consumes the external API.
 *
 * @author Gabriel Pivoto
 * @version JDK 1.7
 * @since 1.0
 */
@Service
@Slf4j
public class Adapter {

    @Value("${omdb.api.url}")
    private String url;

    @Value("${omdb.api.key}")
    private String key;

    /**
     * Given the title of a show, this method returns a Show instance with all its information.
     *
     * @param title - the title of the show
     * @return a Show instance with the show's information
     */
    public Show getShowByTitle(String title) {

        return WebClient.builder().baseUrl("http://" + url).build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("apikey", key)
                        .queryParam("t", title)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Show.class).block();

    }

}
