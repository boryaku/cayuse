package com.cayuse.demo.repos;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class GoogleTimezoneRepo implements TimezoneRepo {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${google.timezone.url}")
    private String googleTimezoneUrl;

    @Value("${google.apiKey}")
    private String googleApiKey;

    @Override
    public String findByLatAndLon(String lat, String lon) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(googleTimezoneUrl)
                .queryParam("location", lat+","+lon)
                .queryParam("timestamp", "1331161200")
                .queryParam("key", googleApiKey);

        JsonNode timezoneReponse =
                restTemplate.getForObject(builder.toUriString(), JsonNode.class);

        return timezoneReponse.get("timeZoneName").asText();
    }
}
