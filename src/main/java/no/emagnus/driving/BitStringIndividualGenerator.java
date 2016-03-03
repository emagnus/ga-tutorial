package no.emagnus.driving;

import no.emagnus.ga.Individual;
import no.emagnus.ga.IndividualGenerator;

import java.util.Random;

public class BitStringIndividualGenerator implements IndividualGenerator {

    public Individual generateSpecimen(int size) {
        Random random = new Random();

        // TODO Implement a proper generator
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < 42; i++) {
            sb.append(random.nextInt(2));
        }
        //sb.append("111011100110011");

        return new Individual(sb.toString());
    }
}
