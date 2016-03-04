package no.emagnus.ga;

import java.util.List;

public interface Recombinator {

    List<Individual> crossover(Individual parent1, Individual parent2);

}
