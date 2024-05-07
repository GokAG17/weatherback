package com.weather.weatherforecast.weather;

import java.io.Serializable;

public class WeatherData implements Serializable {
    private String description;
    private double temperature;
    private int humidity;
    private double windSpeed;
    private double rainVolume; // Rainfall in the last 1 hour
    private int visibility;
    private String mainWeather; // Main weather type (Rain, Clear, etc.)
    private String iconCode; // Icon code for weather representation
    private int cloudiness; // Cloudiness percentage
    private long sunriseTime; // Time of sunrise
    private long sunsetTime; // Time of sunset
    private double latitude; // Latitude coordinate
    private double longitude; // Longitude coordinate

    public WeatherData(String description, double temperature, int humidity, double windSpeed,
                       double rainVolume, int visibility, String mainWeather, String iconCode,
                       int cloudiness, long sunriseTime, long sunsetTime,
                       double latitude, double longitude) {
        this.description = description;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.rainVolume = rainVolume;
        this.visibility = visibility;
        this.mainWeather = mainWeather;
        this.iconCode = iconCode;
        this.cloudiness = cloudiness;
        this.sunriseTime = sunriseTime;
        this.sunsetTime = sunsetTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getRainVolume() {
        return rainVolume;
    }

    public int getVisibility() {
        return visibility;
    }

    public String getMainWeather() {
        return mainWeather;
    }

    public String getIconCode() {
        return iconCode;
    }

    public int getCloudiness() {
        return cloudiness;
    }

    public long getSunriseTime() {
        return sunriseTime;
    }

    public long getSunsetTime() {
        return sunsetTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

