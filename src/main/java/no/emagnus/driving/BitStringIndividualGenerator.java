package no.emagnus.driving;

import no.emagnus.ga.Individual;
import no.emagnus.ga.IndividualGenerator;

import java.util.Random;

public class BitStringIndividualGenerator implements IndividualGenerator {

    public Individual generateSpecimen(int size) {
        Random random = new Random();

        // TODO Implement a proper generator
        //String genotype1 = "100111111111111111111111111";
        //String genotype2 = "001111111111111111111111100";
        //String theGenotype = random.nextBoolean() ? genotype1 : genotype2;

        //return new Individual(theGenotype);

        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            sb.append(random.nextInt(2));
        }

        return new Individual(sb.toString());
    }
}
