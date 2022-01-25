// WeatherController.java - Controller for weather
package com.asgteach.weatherservice;
import com.asgteach.weatherservice.domain.Weather;
import com.asgteach.weatherservice.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class WeatherController {

    @Autowired
    private WeatherRepository weatherRepository;

    @GetMapping(path = "/weather/forecast", 
        produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Weather> feed() {
        return weatherRepository.findAll();
    }
}