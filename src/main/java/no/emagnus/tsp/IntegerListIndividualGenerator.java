package no.emagnus.tsp;

import no.emagnus.ga.Individual;
import no.emagnus.ga.IndividualGenerator;

import java.util.Random;

public class IntegerListIndividualGenerator implements IndividualGenerator {

    private int genotypeSize;

    public IntegerListIndividualGenerator(int genotypeSize) {
        this.genotypeSize = genotypeSize;
    }

    @Override
    public Individual generateIndividual() {
        int[] genotype = new int[genotypeSize];

        // TODO Implement a proper generator
        for (int i = 0; i < genotypeSize; i++) {
            genotype[i] = i;
        }

        return new Individual(genotype);
    }
}
