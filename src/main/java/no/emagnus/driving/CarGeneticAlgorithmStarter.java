package no.emagnus.driving;

import no.emagnus.driving.simulation.CarFitnessEvaluator;
import no.emagnus.ga.FitnessProportionateSelector;
import no.emagnus.ga.GeneticAlgorithm;
import no.emagnus.ga.RunConfig;

public class CarGeneticAlgorithmStarter {

    public static void main(String[] args) {
        RunConfig config = new RunConfig();
        config.POPULATION_SIZE = 1;
        config.NUMBER_OF_GENERATIONS = 3;
        config.MUTATION_RATE = 0;
        config.CROSSOVER_RATE = 0;
        config.visualizeStats = true;

        config.individualGenerator = new BitStringIndividualGenerator(42);
        config.fitnessEvaluator = new CarFitnessEvaluator(true);
        config.selector = new FitnessProportionateSelector();
        config.recombinator = new BitStringIndividualRecombinator();
        config.mutator = new BitStringIndividualMutator();

        new GeneticAlgorithm(config).run();
    }
}
