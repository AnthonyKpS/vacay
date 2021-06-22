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
@JsonTypeName(value = "middleagedtraveller")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class MiddleAgedTraveller extends Traveller implements Serializable {

    /**
     * Cosine similarity
     * @param wikiScoredCriteria the wiki scores
     * @return the score
     */
    @Override
    protected double compareScoredCriteria(ScoredCriteriaMap wikiScoredCriteria) {

        // Unpack the both scores into 2 different arrays
        double[] preferenceScores = ScoredCriteriaMap.unpackScores(wikiScoredCriteria);
        double[] wikiScores = ScoredCriteriaMap.unpackScores(this.getPreferenceScoredCriteriaMap());

        // Calculate the 3 different sums
        double sumA = 0;
        double sumB = 0;
        double sumAB = 0;
        for (int i = 0; i < Criterion.NUMBER_OF; i++) {
            sumA += preferenceScores[i]*preferenceScores[i];
            sumB += wikiScores[i]*wikiScores[i];
            sumAB += preferenceScores[i]*wikiScores[i];
        }
        return sumAB/(Math.sqrt(sumA)*Math.sqrt(sumB));
    }
}
