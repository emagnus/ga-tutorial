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

        System.out.println("Initializing population ...");
        List<Individual> population = initPopulation(config.individualGenerator);

        System.out.println("Running " + config.NUMBER_OF_GENERATIONS + " generations ...");
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

            elitism(newGen);

            population = newGen;
        }
        System.out.println("Done!");

        if (config.visualizeStats) {
            new StatisticsVisualizer().visualize(statistics);
        } else {
            System.out.println(statistics);
        }
    }

    private List<Individual> initPopulation(IndividualGenerator individualGenerator) {
        List<Individual> population = new ArrayList<>();
        for (int i = 0; i < config.POPULATION_SIZE; i++) {
            population.add(individualGenerator.generateIndividual());
        }

        return population;
    }

    private List<Individual> combine(Individual parent1, Individual parent2) {
        // TODO: COMBINE CODE HERE
        Individual child1 = new Individual(parent1.getGenotype());
        Individual child2 = new Individual(parent2.getGenotype());
        return Arrays.asList(child1, child2);
    }

    private Individual selectIndividual(List<Individual> population) {
        // TODO: SELECTION CODE HERE
        int randomIndex = random.nextInt(population.size());

        return population.get(randomIndex);
    }

    private void evaluateFitness(List<Individual> population) {
        // TODO: FITNESS EVALUATION HERE
        config.fitnessEvaluator.evaluateFitness(population);
    }

    private void mutate(List<Individual> individuals) {
        // TODO: MUTATION CODE HERE
    }

    private void elitism(List<Individual> population) {
        // TODO: ELITISM CODE HERE
    }
}
