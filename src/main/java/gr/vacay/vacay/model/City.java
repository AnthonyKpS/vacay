package gr.vacay.vacay.model;

import gr.vacay.vacay.exception.DuplicateCriterionScoreException;
import gr.vacay.vacay.exception.OutOfBoundsCriterionScoreException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

@Entity
@Table(name = City.TABLE_NAME, uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class City implements Serializable {

    /**
     * MySQL table's name for the {@code City} Entity
     */
    public static final String TABLE_NAME = "cities";

    /**
     * City's id number used for storing
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    Long id;

    /**
     * String representation of a {@code City}'s name
     */
    @Column(unique = true)
    String name;

    /**
     * {@code Location} object representing a {@code City}'s location in lat/long coordinates
     */
    Location location;

    /**
     * {@code scoreCriteriaMap} object holding all the criterion-score pairs
     */
    ScoredCriteriaMap scoredCriteriaMap;

    protected City() {
    }

    public City(Long id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.scoredCriteriaMap = wikiCriteriaCrawler(name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    private ScoredCriteriaMap wikiCriteriaCrawler(String cityName) {
        ScoredCriteriaMap scoredCriteriaMap = new ScoredCriteriaMap();
        for (Criterion criterion :
                Criterion.ALL) {

            // TODO: 5/28/21 Wiki api call for every criterion logic
            try {
                scoredCriteriaMap.addScoredCriteria(criterion, 5d);
            } catch (DuplicateCriterionScoreException | OutOfBoundsCriterionScoreException e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }
        return scoredCriteriaMap;
    }
}
