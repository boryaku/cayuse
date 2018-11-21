package com.cayuse.demo.repos;

import com.cayuse.demo.models.City;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Component
public class OpenWeatherCityRepo implements CityRepo{

    @Autowired
    private RestTemplate restTemplate;

    @Value( "${openweather.url}" )
    private String openWeatherUrl;

    @Value( "${openweather.appid}" )
    private String openWeatherAppId;


    @Override
    public City findByZipCode(String zipCode) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(openWeatherUrl)
                .queryParam("zip", zipCode)
                .queryParam("appid", openWeatherAppId);

        JsonNode weatherResponse =
                restTemplate.getForObject(builder.toUriString(), JsonNode.class);

        return new City(zipCode,
                weatherResponse.get("name").asText(),
                weatherResponse.get("main").get("temp").asText(),
                weatherResponse.get("coord").get("lat").asText(),
                weatherResponse.get("coord").get("lon").asText());
    }
}
