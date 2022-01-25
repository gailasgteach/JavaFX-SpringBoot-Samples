// ReactiveWeatherRepository.java - Reactive weather repository
package com.asgteach.weatherservice.repository;
import com.asgteach.weatherservice.domain.Forecaster;
import com.asgteach.weatherservice.domain.Weather;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Repository
public class ReactiveWeatherRepository implements WeatherRepository {
    @Override
    public Flux<Weather> findAll() {
        //simulate data streaming every 1.5 second.
        return Flux.interval(Duration.ofMillis(1500))
            .onBackpressureDrop()
            .map(this::generateForecast)
            .flatMapIterable(wfc -> wfc);
    }

    private List<Weather> generateForecast(long interval) {
        Weather obj = new Weather(Forecaster.getForecast());
        return Arrays.asList(obj);
    }
}
