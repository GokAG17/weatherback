package com.weather.weatherforecast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ServletComponentScan
@ComponentScan(basePackages = "com.weather.weatherforecast")
public class WeatherforecastApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeatherforecastApplication.class, args);
    }
    
}
