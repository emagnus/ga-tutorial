package no.emagnus.driving;

import no.emagnus.ga.GeneticAlgorithm;
import no.emagnus.ga.RunConfig;

public class CarGeneticAlgorithmStarter {

    public static void main(String[] args) {
        RunConfig config = new RunConfig();
        config.POPULATION_SIZE = 2;
        config.NUMBER_OF_GENERATIONS = 10;
        config.visualizeStats = true;

        config.individualGenerator = new BitStringIndividualGenerator(42);
        config.fitnessEvaluator = new CarFitnessEvaluator(true);

        config.selector = new CarSelector();
        config.combiner = new BitStringIndividualRecombinator();
        config.mutator = new BitStringIndividualMutator();

        new GeneticAlgorithm(config).run();
    }
}
