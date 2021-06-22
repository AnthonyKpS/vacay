package gr.vacay.vacay.model.traveller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import gr.vacay.vacay.model.common.Criterion;
import gr.vacay.vacay.model.common.ScoredCriteriaMap;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@JsonTypeName(value = "youngagedtraveller")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class YoungAgedTraveller extends Traveller implements Serializable {

    @Override
    protected double compareScoredCriteria(ScoredCriteriaMap wikiScoredCriteria) {

        // Unpack the both scores into 2 different arrays
        double[] preferenceScores = ScoredCriteriaMap.unpackScores(wikiScoredCriteria);
        double[] wikiScores = ScoredCriteriaMap.unpackScores(this.getPreferenceScoredCriteriaMap());

        // Calculate the sum
        double sum = 0;
        for (int i = 0; i < Criterion.NUMBER_OF; i++) {
            sum = sum + (preferenceScores[i] - wikiScores[i]) * (preferenceScores[i] - wikiScores[i]);
        }
        return (1 / (1 + Math.sqrt(sum)));
    }
}
