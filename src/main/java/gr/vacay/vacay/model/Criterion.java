package gr.vacay.vacay.model;

import java.io.Serializable;
import java.util.Arrays;

public enum Criterion implements Serializable {

    BUSINESS("corporate offices", "enterprises"),
    ENVIRONMENT("mountains", "beaches"),
    LEISURE("shops", "restaurants");

    /**
     * Number of all the available {@code Criterion} types
     */
    public static final int NUMBER_OF = values().length;

    /**
     * Array holding all the available {@code Criterion} types
     */
    public static final Criterion[] ALL = values();

    /**
     * Array of keywords for each criterion
     */
    private final String[] keywords;

    Criterion(String... keywords) {
        this.keywords = keywords;
    }

    public String[] getKeywords() {
        return keywords;
    }

    @Override
    public String toString() {
        return "Criterion{" +
                "keywords=" + Arrays.toString(keywords) +
                '}';
    }
}
