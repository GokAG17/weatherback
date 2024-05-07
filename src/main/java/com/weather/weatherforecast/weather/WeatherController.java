package com.weather.weatherforecast.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/api/weather")
    public WeatherData getWeatherDataByCity(@RequestParam String city) {
        return weatherService.getWeatherDataByCity(city);
    }

    @GetMapping("/api/weatherByCoordinates")
    public WeatherData getWeatherDataByCoordinates(@RequestParam double latitude, @RequestParam double longitude) {
        return weatherService.getWeatherDataByCoordinates(latitude, longitude);
    }
    
    
}
