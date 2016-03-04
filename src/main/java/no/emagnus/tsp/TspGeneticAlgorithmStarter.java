package no.emagnus.tsp;

import no.emagnus.ga.FitnessProportionateSelector;
import no.emagnus.ga.GeneticAlgorithm;
import no.emagnus.ga.RunConfig;
import no.emagnus.tsp.data.TspData;
import no.emagnus.tsp.data.TspDataPoint;

import java.util.List;

public class TspGeneticAlgorithmStarter {

    public static void main(String[] args) {
        List<TspDataPoint> dataset = TspData.getSmallDataset();

        RunConfig config = new RunConfig();
        config.POPULATION_SIZE = 10;
        config.NUMBER_OF_GENERATIONS = 3;
        config.MUTATION_RATE = 0;
        config.CROSSOVER_RATE = 0;
        config.visualizeStats = true;

        config.individualGenerator = new IntegerListIndividualGenerator(dataset.size());
        config.fitnessEvaluator = new TspFitnessEvaluator(true, dataset);
        config.selector = new FitnessProportionateSelector();
        config.recombinator = new IntegerListIndividualRecombinator();
        config.mutator = new IntegerListIndividualMutator();

        new GeneticAlgorithm(config).run();
    }
}
