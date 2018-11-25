package com.cayuse.demo.repos;

import com.cayuse.demo.exceptions.RemoteException;
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
public class GoogleElevationRepo implements ElevationRepo {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private RestTemplate restTemplate;

    @Value("${google.elevation.url}")
    private String googleElevationUrl;

    @Value("${google.apiKey}")
    private String googleApiKey;

    @Autowired
    public GoogleElevationRepo(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private final static String ELEVATION_EXCEPTION = "elevation_exception";

    @Override
    public String findByLatAndLon(String lat, String lon) {
        logger.debug("finding elevation info for {},{}", lat, lon);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(googleElevationUrl)
                .queryParam("locations", lat+","+lon)
                .queryParam("key", googleApiKey);

        JsonNode elevationResponse;

        try {
            logger.debug("calling google url {} for lat lon {}", googleElevationUrl, lat, lon);

            elevationResponse =
                    restTemplate.getForObject(builder.toUriString(), JsonNode.class);

            logger.debug("response successful from google {}", elevationResponse);
        }catch (HttpClientErrorException e){
            logger.error("exception getting elevation from google", e);

            Map<String, Object> extensions = new HashMap<>();
            int statusCode = e.getStatusCode().value();

            if(statusCode == 404){
                extensions.put("elevation_not_found", lat+","+lon);
                throw new RemoteException(ELEVATION_EXCEPTION, extensions);
            } else {
                extensions.put("exception", e.getMessage());
                throw new RemoteException(ELEVATION_EXCEPTION, extensions);
            }
        }

        return elevationResponse == null ? null : elevationResponse.get("results").get(0).get("elevation").asText();
    }
}
