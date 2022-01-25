// WeatherRepository.java - WeatherRepository interface
package com.asgteach.weatherservice.repository;
import com.asgteach.weatherservice.domain.Weather;
import reactor.core.publisher.Flux;

public interface WeatherRepository {
    Flux<Weather> findAll();
}
