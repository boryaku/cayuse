package com.cayuse.demo.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Component
public class CityResolver implements GraphQLResolver<City> {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${google.timezone.url}")
    private String googleTimezoneUrl;

    @Value("${google.elevation.url}")
    private String googleElevationUrl;

    @Value("${google.apiKey}")
    private String googleApiKey;


    /**
     * Resolve the timezone from google api using the city's lat/lon.
     *
     * NOTE: This is only called if the field is requested.
     * @param city
     * @return
     */
    public String getTimeZone(City city){

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(googleTimezoneUrl)
                .queryParam("location", city.lat+","+city.lon)
                .queryParam("timestamp", "1331161200")
                .queryParam("key", googleApiKey);

        JsonNode timezoneReponse =
                restTemplate.getForObject(builder.toUriString(), JsonNode.class);

        return timezoneReponse.get("timeZoneName").asText();
    }


    /**
     * Resolve the elevation from google api using the city's lat/lon.
     *
     * NOTE: This is only called if the field is requested.
     * @param city
     * @return
     */
    public String getElevation(City city){

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(googleElevationUrl)
                .queryParam("locations", city.lat+","+city.lon)
                .queryParam("key", googleApiKey);

        JsonNode elevationReponse =
                restTemplate.getForObject(builder.toUriString(), JsonNode.class);

        return elevationReponse.get("results").get(0).get("elevation").asText();
    }
}
