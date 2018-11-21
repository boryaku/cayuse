package com.cayuse.demo.resolvers;


import com.coxautodev.graphql.tools.GraphQLResolver;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;

@Component
public class CityResolver implements GraphQLResolver<City> {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${google.timezone.url}")
    private String googleTimezoneUrl;

    @Value("${google.timezone.apiKey}")
    private String googleTimezoneApiKey;


    /**
     * Resolve the timezone from google api using the city's lat/lon. NOTE: This all is called if it's requested.
     * @param city
     * @return
     */
    public String getTimeZone(City city){

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(googleTimezoneUrl)
                .queryParam("location", city.lat+","+city.lon)
                .queryParam("timestamp", "1331161200")
                .queryParam("key", googleTimezoneApiKey);

        JsonNode timezoneReponse =
                restTemplate.getForObject(builder.toUriString(), JsonNode.class);

        return timezoneReponse.get("timeZoneName").asText();
    }


    /**
     *
     * @param city
     * @return
     */
    public String getElevation(City city){
        return "100 ft";
    }
}
