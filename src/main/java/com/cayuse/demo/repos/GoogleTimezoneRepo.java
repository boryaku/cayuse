package com.cayuse.demo.repos;

import com.cayuse.demo.exceptions.RemoteException;
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
public class GoogleTimezoneRepo implements TimezoneRepo {

    private RestTemplate restTemplate;

    @Value("${google.timezone.url}")
    private String googleTimezoneUrl;

    @Value("${google.apiKey}")
    private String googleApiKey;

    private final static String TIMEZONE_EXCEPTION = "timezone_exception";

    @Autowired
    public GoogleTimezoneRepo(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String findByLatAndLon(String lat, String lon) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(googleTimezoneUrl)
                .queryParam("location", lat+","+lon)
                .queryParam("timestamp", "1331161200")
                .queryParam("key", googleApiKey);

        JsonNode timezoneResponse;

        try {
            timezoneResponse =
                    restTemplate.getForObject(builder.toUriString(), JsonNode.class);
        }catch (HttpClientErrorException e){
                Map<String, Object> extensions = new HashMap<>();
                int statusCode = e.getStatusCode().value();

                if(statusCode == 404){
                    extensions.put("timezone_not_found", lat+","+lon);
                    throw new RemoteException(TIMEZONE_EXCEPTION, extensions);
                } else {
                    extensions.put("exception", e.getMessage());
                    throw new RemoteException(TIMEZONE_EXCEPTION, extensions);
                }
            }

        return timezoneResponse == null ? null : timezoneResponse.get("timeZoneName").asText();
    }
}
