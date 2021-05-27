package gr.vacay.vacay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Given city is a duplicate.")
public class DuplicateCityNameException extends RuntimeException {
    public DuplicateCityNameException() {
        super();
    }
}
