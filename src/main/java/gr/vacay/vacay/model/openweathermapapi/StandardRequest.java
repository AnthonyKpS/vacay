package gr.vacay.vacay.model.openweathermapapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import gr.vacay.vacay.exception.MediaWikiApiRequestFailureException;
import gr.vacay.vacay.model.common.Location;
import lombok.Data;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;
import java.time.Duration;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StandardRequest implements Serializable {

    @JsonProperty("coord")
    private Coord coord;

    @Data
    private static class Coord implements Serializable {
        @JsonProperty("lon")
        private double longitude;

        @JsonProperty("lat")
        private double latitude;
    }

    /*
    UTILITY
        METHODS
     */
    public Location getCoordinates() {
        return new Location(getCoord().getLatitude(), getCoord().getLongitude());
    }

    public static StandardRequest request(String queryString) {

        // Configure a RestTemplate
        Duration timeout = Duration.ofMinutes(2);
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder
                .setConnectTimeout(timeout)
                .setReadTimeout(timeout)
                .build();

        // Prepare the URI
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString("https://api.openweathermap.org/data/2.5/weather")
                .queryParam("q", queryString)
                .queryParam("appid", "04cbec3f1c0c729fc04ef396bd3cd1df");

        // Make the request
        StandardRequest response = restTemplate.getForObject(uri.toUriString(), StandardRequest.class);
        if (response == null) {
            throw new MediaWikiApiRequestFailureException();
        }
        return response;
    }
}
