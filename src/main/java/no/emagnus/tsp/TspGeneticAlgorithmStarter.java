package no.emagnus.tsp;

import no.emagnus.driving.BitStringIndividualGenerator;
import no.emagnus.ga.GeneticAlgorithm;
import no.emagnus.ga.RunConfig;

public class TspGeneticAlgorithmStarter {
    public static void main(String[] args) {
        RunConfig config = new RunConfig();
        config.POPULATION_SIZE = 10;
        config.NUMBER_OF_GENERATIONS = 10;
        config.visualizeStats = true;
        config.individualGenerator = new BitStringIndividualGenerator(32);
        config.fitnessEvaluator = new TspFitnessEvaluator(true);

        new GeneticAlgorithm(config).run();
    }
}
