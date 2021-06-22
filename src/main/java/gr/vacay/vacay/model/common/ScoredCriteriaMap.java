package gr.vacay.vacay.model.common;

import gr.vacay.vacay.exception.DuplicateCriterionScoreException;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

@Data
public class ScoredCriteriaMap implements Serializable, Iterable<Criterion> {

    /**
     * {@code HashMap} containing all the criteria and their respective scores
     */
    private final HashMap<Criterion, Double> scoredCriteria;

    public ScoredCriteriaMap() {
        this.scoredCriteria = new HashMap<>(Criterion.NUMBER_OF);
    }

    public Double addScoredCriteria(Criterion criterion, Double score) {

        /*
        No duplicate criteria are allowed
         */
        if (scoredCriteria.containsKey(criterion))
            throw new DuplicateCriterionScoreException();

        return scoredCriteria.put(criterion, score);
    }

    public static double[] unpackScores(ScoredCriteriaMap scoredCriteriaMap) {
        double[] scores = new double[Criterion.NUMBER_OF];
        int index = 0;
        for (double score :
                scoredCriteriaMap.getScoredCriteria().values()) {
            scores[index] = score;
            index++;
        }
        return scores;
    }

    @NotNull
    @Override
    public Iterator<Criterion> iterator() {
        return scoredCriteria.keySet().iterator();
    }

    @Override
    public void forEach(Consumer<? super Criterion> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<Criterion> spliterator() {
        return Iterable.super.spliterator();
    }
}
