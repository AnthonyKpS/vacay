package gr.vacay.vacay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Something went wrong while talking to the OpenWeather API.")
public class OpenWeatherApiRequestFailureException extends RuntimeException {
    public OpenWeatherApiRequestFailureException() {
        super();
    }
}
