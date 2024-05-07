package com.weather.weatherforecast.weather;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    private static final String API_KEY = "5d224d627ceaa13b9c3ba8447845fc1e";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather";

    public WeatherData getWeatherDataByCity(String city) {
        try {
            String url = API_URL + "?q=" + city + "&appid=" + API_KEY;
            return getWeatherData(url);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public WeatherData getWeatherDataByCoordinates(double latitude, double longitude) {
        try {
            String url = API_URL + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + API_KEY;
            return getWeatherData(url);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private WeatherData getWeatherData(String url) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);
            JSONObject jsonData = new JSONObject(response);

            JSONArray weatherArray = jsonData.getJSONArray("weather");
            JSONObject weatherInfo = weatherArray.getJSONObject(0);

            String description = weatherInfo.getString("description");
            double temperature = jsonData.getJSONObject("main").getDouble("temp");
            int humidity = jsonData.getJSONObject("main").getInt("humidity");
            double windSpeed = jsonData.getJSONObject("wind").getDouble("speed");
            double rainVolume = 0; // Assuming there might not be rain, modify accordingly
            int visibility = jsonData.getInt("visibility");
            String mainWeather = weatherInfo.getString("main");
            String iconCode = weatherInfo.getString("icon");
            int cloudiness = jsonData.getJSONObject("clouds").getInt("all");
            long sunriseTime = jsonData.getJSONObject("sys").getLong("sunrise");
            long sunsetTime = jsonData.getJSONObject("sys").getLong("sunset");
            double fetchedLatitude = jsonData.getJSONObject("coord").getDouble("lat");
            double fetchedLongitude = jsonData.getJSONObject("coord").getDouble("lon");

            return new WeatherData(description, temperature, humidity, windSpeed,
                    rainVolume, visibility, mainWeather, iconCode,
                    cloudiness, sunriseTime, sunsetTime,
                    fetchedLatitude, fetchedLongitude);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
