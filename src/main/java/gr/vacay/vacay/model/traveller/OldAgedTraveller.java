package gr.vacay.vacay.model.traveller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import gr.vacay.vacay.model.common.Criterion;
import gr.vacay.vacay.model.common.ScoredCriteriaMap;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@JsonTypeName(value = "oldagedtraveller")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class OldAgedTraveller extends Traveller implements Serializable {

    @Override
    protected double compareScoredCriteria(ScoredCriteriaMap wikiScoredCriteria) {

        // Unpack the both scores into 2 different arrays
        double[] preferenceScores = ScoredCriteriaMap.unpackScores(wikiScoredCriteria);
        double[] wikiScores = ScoredCriteriaMap.unpackScores(this.getPreferenceScoredCriteriaMap());

        int commonTerms = 0;
        for (int i = 0; i < Criterion.NUMBER_OF; i++) {
            for (int j = 0; j < Criterion.NUMBER_OF; j++) {
                if (preferenceScores[i] == wikiScores[j]) {
                    commonTerms++;
                    break; // not admeasuring double occurrence
                }
            }
        }
        return commonTerms / (Criterion.NUMBER_OF * 2d - commonTerms);
    }
}
