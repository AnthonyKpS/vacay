package gr.vacay.vacay.model.city;

import com.fasterxml.jackson.annotation.JsonProperty;
import gr.vacay.vacay.exception.DuplicateCriterionScoreException;
import gr.vacay.vacay.exception.OutOfBoundsCriterionScoreException;
import gr.vacay.vacay.model.common.Criterion;
import gr.vacay.vacay.model.common.Location;
import gr.vacay.vacay.model.common.ScoredCriteriaMap;
import gr.vacay.vacay.model.mediawikiapi.ParseRequest;
import gr.vacay.vacay.model.openweathermapapi.StandardRequest;
import gr.vacay.vacay.model.traveller.Traveller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "City")
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "city_wiki_page_id_unique", columnNames = "wiki_page_id")
        }
)
public class City implements Serializable {

    @Id
    @SequenceGenerator(
            name = "city_sequence",
            sequenceName = "city_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "city_sequence"
    )
    @Column(
            name = "city_id",
            updatable = false
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(
            name = "wiki_page_id",
            updatable = false,
            nullable = false
    )
    @JsonProperty("wikipageid")
    private int wikiPageId;

    @Column(
            name = "title",
            updatable = false,
            nullable = false
    )
    @JsonProperty("title")
    private String title;

    @Column(
            name = "location",
            updatable = false,
            nullable = false
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Location location;

    @Lob
    @Column(
            name = "wiki_scored_criteria_map",
            updatable = false,
            nullable = false,
            columnDefinition = "LONGBLOB"
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ScoredCriteriaMap wikiScoredCriteriaMap;

    @ManyToMany(mappedBy = "cities")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private List<Traveller> travellers;

    /*
    UTILITY
        METHODS
     */
    public void augmentCity() {

        // Make the request to the WikiMedia Api
        ParseRequest parseResponse = ParseRequest.request(this.wikiPageId);

        // Make the request to the Open Weather Map Api
        StandardRequest coordinatesResponse = StandardRequest.request(this.title);

        // Distribute the responses
        this.wikiScoredCriteriaMap = fillScoredCriteriaMapFromWikiPageContent(parseResponse.getContent());
        this.location = coordinatesResponse.getCoordinates();
    }

    public static void transferAugmentedCityFields(City source, City target) {
        target.setLocation(source.getLocation());
        target.setWikiScoredCriteriaMap(source.getWikiScoredCriteriaMap());
    }

    private static ScoredCriteriaMap fillScoredCriteriaMapFromWikiPageContent(String wikiPageContent) {

        // Make a ScoredCriteriaMap that will hold the scores
        ScoredCriteriaMap wikiScoredCriteriaMap = new ScoredCriteriaMap();

        for (Criterion criterion :
                Criterion.ALL) {

            // Get the sum of the occurrences of all the keywords
            Long keywordsOccurrences = countKeywordOccurrencesInAString(wikiPageContent, criterion.getKeywords());

            try {
                wikiScoredCriteriaMap.addScoredCriteria(criterion, keywordsOccurrences.doubleValue());
            } catch (DuplicateCriterionScoreException | OutOfBoundsCriterionScoreException e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }
        return wikiScoredCriteriaMap;
    }

    private static Long countKeywordOccurrencesInAString(String content, String[] keywords) {

        long sum = 0;
        for (String keyword :
                keywords) {
            Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(content);
            sum += matcher.results().count();
        }
        return sum;
    }
}
