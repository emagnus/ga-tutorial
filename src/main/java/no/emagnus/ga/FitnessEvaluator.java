package no.emagnus.ga;

import java.util.Collection;

public interface FitnessEvaluator<T> {

    void evaluateFitness(Collection<Individual<T>> individual);
}
