package br.com.inatel.MyCatalog.adapter;

import br.com.inatel.MyCatalog.model.rest.Show;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class Adapter {

    @Value("${omdb.api.url}")
    private String url;

    @Value("${omdb.api.key}")
    private String key;

    public Show getShowByTitle(String title){

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
