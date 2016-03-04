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

        for (int i = 0; i < genotypeSize; i++) {
            genotype[i] = i;
        }

        shuffleArray(genotype);

        return new Individual(genotype);
    }

    private void shuffleArray(int[] array) {
        int index;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            if (index != i) {
                array[index] ^= array[i];
                array[i] ^= array[index];
                array[index] ^= array[i];
            }
        }
    }
}
