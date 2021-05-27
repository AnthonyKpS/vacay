package gr.vacay.vacay.model;

import gr.vacay.vacay.exception.DuplicateCriterionScoreException;
import gr.vacay.vacay.exception.OutOfBoundsCriterionScoreException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ScoredCriteriaMap implements Serializable {

    /**
     * {@code HashMap} containing all the criteria and their respective scores
     */
    private final Map<Criterion, Double> scoredCriteria;

    public ScoredCriteriaMap() {
        this.scoredCriteria = new HashMap<>(Criterion.NUMBER_OF);
    }

    public Map<Criterion, Double> getScoredCriteria() {
        return scoredCriteria;
    }

    public Double addScoredCriteria(Criterion criterion, Double score) {

        /*
        No duplicate criteria are allowed
         */
        if (scoredCriteria.containsKey(criterion))
            throw new DuplicateCriterionScoreException();

        /*
        All scoring to entered here should be canonicalized to a range of values between [0-10]
         */
        if (score >= 10 || score <= 0)
            throw new OutOfBoundsCriterionScoreException();

        return scoredCriteria.put(criterion, score);
    }
}
