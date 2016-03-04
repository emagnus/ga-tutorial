package no.emagnus.tsp;

import no.emagnus.ga.Individual;
import no.emagnus.ga.Recombinator;

import java.util.Arrays;
import java.util.List;

public class IntegerListIndividualRecombinator implements Recombinator {

    @Override
    public List<Individual> crossover(Individual parent1, Individual parent2) {
        // TODO Implement proper crossover
        return Arrays.asList(parent1.copy(), parent2.copy());
    }
}
