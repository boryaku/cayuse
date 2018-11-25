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
public class GoogleTimezoneRepo implements TimezoneRepo {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

        logger.debug("finding timezone info for {},{}", lat, lon);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(googleTimezoneUrl)
                .queryParam("location", lat+","+lon)
                .queryParam("timestamp", "1331161200")
                .queryParam("key", googleApiKey);

        JsonNode timezoneResponse;

        try {
            logger.debug("calling google url {} for lat lon {}", googleTimezoneUrl, lat, lon);

            timezoneResponse =
                    restTemplate.getForObject(builder.toUriString(), JsonNode.class);

            logger.debug("response successful from google {}", timezoneResponse);
        }catch (HttpClientErrorException e){
            logger.error("exception getting timezone from google", e);

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
