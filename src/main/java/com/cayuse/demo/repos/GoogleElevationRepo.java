package com.cayuse.demo.repos;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Component
public class GoogleElevationRepo implements ElevationRepo {

    private RestTemplate restTemplate;

    @Value("${google.elevation.url}")
    private String googleElevationUrl;

    @Value("${google.apiKey}")
    private String googleApiKey;

    @Autowired
    public GoogleElevationRepo(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String findByLatAndLon(String lat, String lon) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(googleElevationUrl)
                .queryParam("locations", lat+","+lon)
                .queryParam("key", googleApiKey);

        JsonNode elevationReponse =
                restTemplate.getForObject(builder.toUriString(), JsonNode.class);

        return elevationReponse.get("results").get(0).get("elevation").asText();
    }
}
