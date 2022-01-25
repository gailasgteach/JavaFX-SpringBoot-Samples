// WeatherClient.java - Reactive weather client
package com.asgteach.weatherclient.service;
import com.asgteach.weatherclient.domain.Weather;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class WeatherClient {
    private final WebClient webClient;

    public WeatherClient(WebClient.Builder webClientBuilder) {
        webClient = 
            webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    public Flux<Weather> getWeatherForecast() {
        return webClient.get()
            .uri("/weather/forecast")
            .retrieve()
            .bodyToFlux(Weather.class);
    }
}
