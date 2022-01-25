// MusicRESTService.java - Music REST service
package com.asgteach.springboot.service;
import com.asgteach.springboot.domain.MusicCategory;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MusicRESTService {
    private final WebClient webClient;

    public MusicRESTService(WebClient.Builder webClientBuilder) {
        webClient = 
            webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    private static final Logger log = 
            LoggerFactory.getLogger(MusicRESTService.class);

    public List<MusicCategory> getMusicCategories() {
        List<MusicCategory> result = webClient.get()
            .uri("/musiccategories")
            .retrieve()
            .bodyToFlux(MusicCategory.class)
            .collectList()
            .block();
        result.forEach(mc -> log.info(mc.toString()));
        return result;
    }

    public MusicCategory getMusicCategory(Integer id) {
        MusicCategory mc = webClient.get().uri("/{id}/", id)
            .retrieve()
            .bodyToMono(MusicCategory.class)
            .block();
        log.info(mc + " read.");
        return mc;
    }

    public MusicCategory createMusicCategory(String category) {
        MusicCategory mc = new MusicCategory();
        mc.setMusicCategory(category);
        mc = webClient.post()
            .uri("/musiccategories")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(Mono.just(mc), MusicCategory.class)
            .retrieve()
            .bodyToMono(MusicCategory.class)
            .block();
        log.info(mc + " created.");
        return mc;
    }

    public MusicCategory updateMusicCategory(MusicCategory mc) {
        MusicCategory musicCategory = webClient.put()
            .uri("/musiccategories/" + mc.getMusicCategoryId())
            .body(Mono.just(mc), MusicCategory.class)
            .retrieve()
            .bodyToMono(MusicCategory.class)
            .block();
        log.info(musicCategory + " updated.");
        return musicCategory;
    }

    public void deleteMusicCategory(MusicCategory mc) {
        webClient.delete()
            .uri("/musiccategories/" + mc.getMusicCategoryId())
            .retrieve()
            .bodyToMono(Void.class)
            .block();
        log.info(mc + " deleted.");
    }
}
