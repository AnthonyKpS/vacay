package gr.vacay.vacay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Something went wrong while talking to the wikimedia API.")
public class MediaWikiApiRequestFailureException extends RuntimeException {
    public MediaWikiApiRequestFailureException() {
        super();
    }
}
