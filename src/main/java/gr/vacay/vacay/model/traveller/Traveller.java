package gr.vacay.vacay.model.traveller;

import com.fasterxml.jackson.annotation.*;
import gr.vacay.vacay.model.AuditModel;
import gr.vacay.vacay.model.city.City;
import gr.vacay.vacay.model.common.Location;
import gr.vacay.vacay.model.common.ScoredCriteriaMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName(value = "traveller")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = YoungAgedTraveller.class, name = "youngagedtraveller"),
        @JsonSubTypes.Type(value = MiddleAgedTraveller.class, name = "middleagedtraveller"),
        @JsonSubTypes.Type(value = OldAgedTraveller.class, name = "oldagedtraveller")
})
public abstract class Traveller extends AuditModel implements Serializable {

    @Id
    @SequenceGenerator(
            name = "traveller_sequence",
            sequenceName = "traveller_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "traveller_sequence"
    )
    @Column(
            name = "traveller_id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "age",
            updatable = false,
            nullable = false
    )
    @JsonProperty("age")
    private int age;

    @Column(
            name = "location",
            updatable = false,
            nullable = false
    )
    @JsonProperty("location")
    private Location location;

    @ManyToMany
    @JoinTable(
            name = "traveller_cities",
            joinColumns = @JoinColumn(name = "traveller_id"),
            inverseJoinColumns = @JoinColumn(name = "city_id")
    )
    @JsonProperty("cities")
    private List<City> cities;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    @JsonProperty("preferences")
    private ScoredCriteriaMap preferenceScoredCriteriaMap;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int matchingCityWikiPageId;

    /*
    UTILITY
        METHODS
     */
    public City findMatchingCity() {

        double similarity;
        double maxSimilarity = 0d; // smallest similarity value possible
        City maxCity = cities.get(0);

        for (City city :
                cities) {
            similarity = calculateCitySimilarityWithTraveller(city);
            if (similarity >= maxSimilarity) {
                maxSimilarity = similarity;
                maxCity = city;
            }
        }
        return maxCity;
    }

    private double calculateCitySimilarityWithTraveller(City city) {
        double p = .4d;
        double firstTerm = p * compareScoredCriteria(city.getWikiScoredCriteriaMap());
        double secondTerm = (1-p) * compareLocations(city.getLocation());
        return firstTerm + secondTerm;
    }

    protected abstract double compareScoredCriteria(ScoredCriteriaMap wikiScoredCriteria);

    private double compareLocations(Location cityLocation) {

        // Indirect calculation of a base 2 logarithm | Using: log2 N = log10 N / log10 2
        double distBetweenLocations = Location.getDistBetweenTwo(location, cityLocation);
        double N = (2 / (2 - (distBetweenLocations / Location.MAX_DIST_BETWEEN_CITIES)));
        return Math.log10(Math.abs(N)) / Math.log10(2);
    }

}

