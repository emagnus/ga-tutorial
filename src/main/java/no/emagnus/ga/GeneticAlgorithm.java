package no.emagnus.ga;

import no.emagnus.statistics.Statistics;
import no.emagnus.statistics.StatisticsVisualizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {

    private final RunConfig config;
    private Random random = new Random();

    public GeneticAlgorithm(RunConfig config) {
        this.config = config;
    }

    public void run() {
        // statistics to visualize
        Statistics statistics = new Statistics(config.NUMBER_OF_GENERATIONS);

        List<Individual> population = initPopulation(config.individualGenerator);

        for (int gen = 0; gen < config.NUMBER_OF_GENERATIONS; gen++) {
            evaluateFitness(population);

            // record stats about the population
            statistics.recordMaxAndAvg(gen, population);

            List<Individual> newGen = new ArrayList<>();
            while (newGen.size() < config.POPULATION_SIZE) {
                Individual parent1 = selectIndividual(population);
                Individual parent2 = selectIndividual(population);
                List<Individual> children = combine(parent1, parent2);

                mutate(children);
                newGen.addAll(children);
            }

            elitistSelection(population, newGen);

            population = newGen;
        }

        if (config.visualizeStats) {
            new StatisticsVisualizer().visualize(statistics);
        } else {
            System.out.println(statistics);
        }
    }

    private List<Individual> initPopulation(IndividualGenerator individualGenerator) {
        System.out.println("Initializing population ...");
        List<Individual> population = new ArrayList<>();
        for (int i = 0; i < config.POPULATION_SIZE; i++) {
            population.add(individualGenerator.generateIndividual());
        }

        return population;
    }

    private void evaluateFitness(List<Individual> population) {
        config.fitnessEvaluator.evaluateFitness(population);
    }

    private Individual selectIndividual(List<Individual> population) {
        return config.selector.select(population);
    }

    private List<Individual> combine(Individual parent1, Individual parent2) {
        if (random.nextDouble() < config.CROSSOVER_RATE) {
            return config.combiner.crossover(parent1, parent2);
        } else {
            return Arrays.asList(parent1.copy(), parent2.copy());
        }
    }

    private void mutate(List<Individual> individuals) {
        for (Individual individual : individuals) {
            if (random.nextDouble() < config.MUTATION_RATE) {
                config.mutator.mutate(individual);
            }
        }
    }

    private void elitistSelection(List<Individual> population, List<Individual> newGen) {
        // TODO: ELITISM CODE HERE
    }
}
