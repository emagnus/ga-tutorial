package no.emagnus.biggestnumber;

import no.emagnus.ga.Individual;
import no.emagnus.ga.IndividualGenerator;

import java.util.Random;

public class NumberIndividualGenerator implements IndividualGenerator<String> {

    public Individual<String> generateSpecimen() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(2));
        }
        return new Individual<>(sb.toString());
    }
}
