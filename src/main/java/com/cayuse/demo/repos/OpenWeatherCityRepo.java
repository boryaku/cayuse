package com.cayuse.demo.repos;

import com.cayuse.demo.exceptions.RemoteException;
import com.cayuse.demo.models.City;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private RestTemplate restTemplate;

    @Value( "${openweather.url}" )
    private String openWeatherUrl;

    @Value( "${openweather.appid}" )
    private String openWeatherAppId;

    private final static String CITY_EXCEPTION = "city_exception";


    @Override
    public City findByZipCode(String zipCode) {
        logger.debug("finding city info for {}", zipCode);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(openWeatherUrl)
                .queryParam("zip", zipCode)
                .queryParam("appid", openWeatherAppId);

        JsonNode weatherResponse;

        try {
            logger.debug("calling open weather url {} for zip code {}", openWeatherUrl, zipCode);

            weatherResponse =
                    restTemplate.getForObject(builder.toUriString(), JsonNode.class);

            logger.debug("response successful from open weather {}", weatherResponse);
        }catch (HttpClientErrorException e){
            logger.error("exception getting city information from open weather", e);
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
