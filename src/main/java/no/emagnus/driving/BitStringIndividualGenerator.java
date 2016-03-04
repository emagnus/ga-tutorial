package no.emagnus.driving;

import no.emagnus.ga.Individual;
import no.emagnus.ga.IndividualGenerator;

import java.util.Random;

public class BitStringIndividualGenerator implements IndividualGenerator {

    private int genotypeSize;

    public BitStringIndividualGenerator(int genotypeSize) {
        this.genotypeSize = genotypeSize;
    }

    @Override
    public Individual generateIndividual() {
        Random random = new Random();

        // TODO Implement a proper generator
        int[] genotype = new int[genotypeSize];
        genotype[2] = random.nextInt(2);

        return new Individual(genotype);
    }
}
