package no.emagnus.ga;

import no.emagnus.statistics.Statistics;
import no.emagnus.statistics.StatisticsVisualizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {

    private static final int POPULATION_SIZE = 4;
    private static final int NUMBER_OF_GENERATIONS = 10;

    private Random random = new Random();
    private boolean visualizeStats = false;

    private FitnessEvaluator fitnessTester;
    private IndividualGenerator individualGenerator;

    public GeneticAlgorithm(FitnessEvaluator fitnessTester, IndividualGenerator individualGenerator, boolean visualizeStats) {
        this.fitnessTester = fitnessTester;
        this.individualGenerator = individualGenerator;
        this.visualizeStats = visualizeStats;
    }

    public void run() {
        // statistics to visualize
        Statistics statistics = new Statistics(NUMBER_OF_GENERATIONS);

        System.out.println("Initializing population ...");
        List<Individual> population = initPopulation(individualGenerator);

        System.out.println("Running " + NUMBER_OF_GENERATIONS + " generations ...");
        for (int gen = 0; gen < NUMBER_OF_GENERATIONS; gen++) {
            evaluateFitness(population);

            // record stats about the population
            statistics.recordMaxAndAvg(gen, population);

            List<Individual> newGen = new ArrayList<>();
            while (newGen.size() < POPULATION_SIZE) {
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

        if (visualizeStats) {
            new StatisticsVisualizer().visualize(statistics);
        } else {
            System.out.println(statistics);
        }
    }

    private List<Individual> initPopulation(IndividualGenerator individualGenerator) {
        List<Individual> population = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population.add(individualGenerator.generateSpecimen(11));
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
        fitnessTester.evaluateFitness(population);
    }

    private void mutate(List<Individual> individuals) {
        // TODO: MUTATION CODE HERE
    }

    private void elitism(List<Individual> population) {
        // TODO: ELITISM CODE HERE
    }
}
