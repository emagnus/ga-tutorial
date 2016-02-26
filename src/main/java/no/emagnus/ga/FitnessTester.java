package no.emagnus.ga;

public interface FitnessTester<T> {

    double evaluateFitness(T individual);
}
