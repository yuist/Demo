package com.scpoe.GoodWeather.model;

import java.util.List;


public class WeatherDetail {

    //base
    private String cityName;
    private String publishTime;
    //summary
    private String summaryWeather;
    private String summaryTemperatureTop;
    private String summaryTemperatureBottom;
    private String summaryTemperature;
    //forecast
    private List<ForecastDetail> forecastList;
    //detail
    private String detailHumidity;
    private String detailWind;
    private String detailUV;
    private String detailClothes;


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getSummaryWeather() {
        return summaryWeather;
    }

    public void setSummaryWeather(String summaryWeather) {
        this.summaryWeather = summaryWeather;
    }

    public String getSummaryTemperatureTop() {
        return summaryTemperatureTop;
    }

    public void setSummaryTemperatureTop(String summaryTemperatureTop) {
        this.summaryTemperatureTop = summaryTemperatureTop;
    }

    public String getSummaryTemperatureBottom() {
        return summaryTemperatureBottom;
    }

    public void setSummaryTemperatureBottom(String summaryTemperatureBottom) {
        this.summaryTemperatureBottom = summaryTemperatureBottom;
    }

    public String getSummaryTemperature() {
        return summaryTemperature;
    }

    public void setSummaryTemperature(String summaryTemperature) {
        this.summaryTemperature = summaryTemperature;
    }

    public List<ForecastDetail> getForecastList() {
        return forecastList;
    }

    public void setForecastList(List<ForecastDetail> forecastList) {
        this.forecastList = forecastList;
    }

    public String getDetailHumidity() {
        return detailHumidity;
    }

    public void setDetailHumidity(String detailHumidity) {
        this.detailHumidity = detailHumidity;
    }

    public String getDetailWind() {
        return detailWind;
    }

    public void setDetailWind(String detailWind) {
        this.detailWind = detailWind;
    }

    public String getDetailUV() {
        return detailUV;
    }

    public void setDetailUV(String detailUV) {
        this.detailUV = detailUV;
    }

    public String getDetailClothes() {
        return detailClothes;
    }

    public void setDetailClothes(String detailClothes) {
        this.detailClothes = detailClothes;
    }
}
