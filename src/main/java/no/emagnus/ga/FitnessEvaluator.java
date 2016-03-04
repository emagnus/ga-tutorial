package no.emagnus.ga;

import java.util.Collection;

public interface FitnessEvaluator {

    void evaluateFitness(Collection<Individual> population);
}
