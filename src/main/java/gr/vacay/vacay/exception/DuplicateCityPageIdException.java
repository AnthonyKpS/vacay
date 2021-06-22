package gr.vacay.vacay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "City with the same pageId already exists.")
public class DuplicateCityPageIdException extends RuntimeException {
    public DuplicateCityPageIdException() {
        super();
    }
}
