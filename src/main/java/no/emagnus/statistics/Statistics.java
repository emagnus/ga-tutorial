package no.emagnus.statistics;

import no.emagnus.ga.Individual;

import java.util.List;

public class Statistics {
    private double[] maxFitness;
    private double[] avgFitness;

    public Statistics(int numberOfGenerations) {
        maxFitness = new double[numberOfGenerations];
        avgFitness = new double[numberOfGenerations];
    }

    public void recordMaxAndAvg(int gen, List<Individual> population) {
        double acc = 0;
        double max = population.get(0).getFitness();

        for (Individual individual : population) {
            acc += individual.getFitness();
            if (individual.getFitness() > max) {
                max = individual.getFitness();
            }
        }

        maxFitness[gen] = max;
        avgFitness[gen] = acc / population.size();
    }

    public double[] getMaxFitness() {
        return maxFitness;
    }

    public double[] getAvgFitness() {
        return avgFitness;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("======= STATISTICS =======\n");
        for (int gen = 0; gen < maxFitness.length; gen++) {
            sb.append(String.format("G%d\tMax: %.2f\tAvg: %.2f\n", gen, maxFitness[gen], avgFitness[gen]));
        }

        return sb.toString();
    }
}
