package gr.vacay.vacay.model;

import java.io.Serializable;

public record Location(double latitude, double longitude) implements Serializable {

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
