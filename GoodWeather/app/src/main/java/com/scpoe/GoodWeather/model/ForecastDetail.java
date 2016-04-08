package com.scpoe.GoodWeather.model;


public class ForecastDetail {

    private String forecastDay;
    private String forecastDayWeather;
    private String forecastDayTemperatureTop;
    private String forecastDayTemperatureBottom;

    public String getForecastDay() {
        return forecastDay;
    }

    public void setForecastDay(String forecastDay) {
        this.forecastDay = forecastDay;
    }

    public String getForecastDayWeather() {
        return forecastDayWeather;
    }

    public void setForecastDayWeather(String forecastDayWeather) {
        this.forecastDayWeather = forecastDayWeather;
    }

    public String getForecastDayTemperatureTop() {
        return forecastDayTemperatureTop;
    }

    public void setForecastDayTemperatureTop(String forecastDayTemperatureTop) {
        this.forecastDayTemperatureTop = forecastDayTemperatureTop;
    }

    public String getForecastDayTemperatureBottom() {
        return forecastDayTemperatureBottom;
    }

    public void setForecastDayTemperatureBottom(String forecastDayTemperatureBottom) {
        this.forecastDayTemperatureBottom = forecastDayTemperatureBottom;
    }
}
