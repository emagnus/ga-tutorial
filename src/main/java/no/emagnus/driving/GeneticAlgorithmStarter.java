package no.emagnus.driving;

import no.emagnus.ga.FitnessEvaluator;
import no.emagnus.ga.GeneticAlgorithm;
import no.emagnus.ga.IndividualGenerator;

public class GeneticAlgorithmStarter {

    public static void main(String[] args) {
        FitnessEvaluator fitnessTester = new CarFitnessEvaluator(true);
        IndividualGenerator individualGenerator = new BitStringIndividualGenerator();
        new GeneticAlgorithm(fitnessTester, individualGenerator, true).run();
    }
}
