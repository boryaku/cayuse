package com.cayuse.demo.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Component
class Query implements GraphQLQueryResolver {

    @Autowired
    private RestTemplate restTemplate;

    @Value( "${openweather.url}" )
    private String openWeatherUrl;

    @Value( "${openweather.appid}" )
    private String openWeatherAppId;

    /**
     * Root query for the top level city info finder, we use the open weather api to get information to assist
     * the other resolvers ie. timeZone and Elevation
     * @param zipCode
     * @return
     */
    public City getCity(String zipCode){

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


