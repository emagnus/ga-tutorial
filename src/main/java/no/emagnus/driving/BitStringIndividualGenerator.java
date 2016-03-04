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
        StringBuilder sb = new StringBuilder(genotypeSize);
        for (int i = 0; i < genotypeSize; i++) {
            sb.append(random.nextInt(2));
        }
        //sb.append("111011100110011");

        return new Individual(sb.toString());
    }
}
