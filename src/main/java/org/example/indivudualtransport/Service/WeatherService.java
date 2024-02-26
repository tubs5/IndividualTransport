package org.example.indivudualtransport.Service;

import org.example.indivudualtransport.Model.Weather;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Random;

/**
 * @author Tobias Heidlund
 */
@Service
public class WeatherService {
    private final String[] typesOfWeater = {
            "Raining","Clear","Cloudy"
    };
    public Weather getWeather(String to, LocalTime arrivalTime) {
        Random random = new Random();
        return new Weather(random.nextInt(1,30),typesOfWeater[random.nextInt(0,2)]);
    }
}
