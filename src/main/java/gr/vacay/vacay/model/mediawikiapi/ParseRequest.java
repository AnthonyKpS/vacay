package gr.vacay.vacay.model.mediawikiapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import gr.vacay.vacay.exception.MediaWikiApiRequestFailureException;
import lombok.Data;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;
import java.time.Duration;

@Data
@JsonTypeName(value = "parse")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParseRequest implements Serializable {

    @JsonProperty("text")
    private Text text;

    @Data
    private static class Text implements Serializable {

        @JsonProperty("*")
        private String content;
    }

    /*
    UTILITY
        METHODS
     */
    public static ParseRequest request(int wikiPageId) {

        // Configure a RestTemplate
        Duration timeout = Duration.ofMinutes(1);
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder
                .setConnectTimeout(timeout)
                .setReadTimeout(timeout)
                .build();

        // Prepare the URI
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString("https://en.wikivoyage.org/w/api.php")
                .queryParam("action", "parse")
                .queryParam("format", "json")
                .queryParam("pageid", wikiPageId)
                .queryParam("prop", "text");

        // Make the request
        ParseRequest response = restTemplate.getForObject(uri.toUriString(), ParseRequest.class);
        if (response == null) {
            throw new MediaWikiApiRequestFailureException();
        }

        return response;
    }

    public String getContent() {
        return getText().getContent();
    }
}
