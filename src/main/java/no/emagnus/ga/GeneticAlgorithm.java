package no.emagnus.ga;

import no.emagnus.biggestnumber.BiggestNumberFitnessTester;
import no.emagnus.biggestnumber.NumberIndividualGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {

    private static final int POPULATION_SIZE = 50;
    private static final int NUMBER_OF_GENERATIONS = 50;

    private FitnessTester fitnessTester = new BiggestNumberFitnessTester();
    private IndividualGenerator individualGenerator = new NumberIndividualGenerator();
    private Random random = new Random();

    public void run() {
        System.out.println("Initializing population ...");
        List<Individual> population = initPopulation(individualGenerator);

        System.out.println("Running " + NUMBER_OF_GENERATIONS + " generations ...");
        for (int gen = 0; gen < NUMBER_OF_GENERATIONS; gen++) {
            evaluateFitness(population);

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

        double maxFitness = 0.0;
        Individual theBest = population.get(0);
        for (Individual specimen : population) {
            double fitness = specimen.getFitness();
            if (fitness > maxFitness) {
                maxFitness = fitness;
                theBest = specimen;
            }
        }

        System.out.println("Winner: " + theBest + " with fitness " + maxFitness);
    }

    private List<Individual> initPopulation(IndividualGenerator individualGenerator) {
        List<Individual> population = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population.add(individualGenerator.generateSpecimen());
        }

        return population;
    }

    private List<Individual> combine(Individual parent1, Individual parent2) {
        // TODO: COMBINE CODE HERE
        return Arrays.asList(parent1, parent2);
    }

    private Individual selectIndividual(List<Individual> population) {
        // TODO: SELECTION CODE HERE
        int randomIndex = random.nextInt(population.size());

        return population.get(randomIndex);
    }

    private void evaluateFitness(List<Individual> population) {
        // TODO: FITNESS EVALUATION HERE
        for (Individual individual : population) {
            individual.setFitness(fitnessTester.evaluateFitness(individual.getGenotype()));
        }
    }

    private void mutate(List<Individual> individuals) {
        // TODO: MUTATION CODE HERE
    }

    private void elitism(List<Individual> population) {
        // TODO: ELITISM CODE HERE
    }
}
