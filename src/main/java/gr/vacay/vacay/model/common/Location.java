package gr.vacay.vacay.model.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Location implements Serializable {

    /**
     * Half of the Earth's circumference along a Great Circle route that passes through that point,
     * roughly 12.000 miles, though it varies a tiny bit from point to point.
     * The longest distance between any two places on earth is, 7,926 miles (12,756 km).
     */
    public static final double MAX_DIST_BETWEEN_CITIES = 12756d;

    private final double latitude;

    private final double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /*
    UTILITY
        METHODS
     */
    public static double getDistBetweenTwo(Location location1, Location location2) {
        if (location1.equals(location2)) {
            return 0;
        }
        double theta = location1.longitude - location2.getLongitude();
        double dist = Math.sin(Math.toRadians(location1.latitude)) * Math.sin(Math.toRadians(location2.latitude)) +
                Math.cos(Math.toRadians(location1.latitude)) * Math.cos(Math.toRadians(location2.latitude)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515 * 1.609344; // Assuming KM
        return dist;
    }
}
