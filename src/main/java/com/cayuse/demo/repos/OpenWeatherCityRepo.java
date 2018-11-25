package com.cayuse.demo.repos;

import com.cayuse.demo.exceptions.RemoteException;
import com.cayuse.demo.models.City;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;


@Component
public class OpenWeatherCityRepo implements CityRepo{

    @Autowired
    private RestTemplate restTemplate;

    @Value( "${openweather.url}" )
    private String openWeatherUrl;

    @Value( "${openweather.appid}" )
    private String openWeatherAppId;

    private final static String CITY_EXCEPTION = "city_exception";


    @Override
    public City findByZipCode(String zipCode) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(openWeatherUrl)
                .queryParam("zip", zipCode)
                .queryParam("appid", openWeatherAppId);

        JsonNode weatherResponse;

        try {
            weatherResponse =
                    restTemplate.getForObject(builder.toUriString(), JsonNode.class);
        }catch (HttpClientErrorException e){
            Map<String, Object> extensions = new HashMap<>();
            int statusCode = e.getStatusCode().value();

            if(statusCode == 404){
                extensions.put("city_not_found", zipCode);
                throw new RemoteException(CITY_EXCEPTION, extensions);
            } else {
                extensions.put("exception", e.getMessage());
                throw new RemoteException(CITY_EXCEPTION, extensions);
            }
        }

        return weatherResponse == null ? null : new City(zipCode,
                weatherResponse.get("name").asText(),
                weatherResponse.get("main").get("temp").asText(),
                weatherResponse.get("coord").get("lat").asText(),
                weatherResponse.get("coord").get("lon").asText());
    }
}
