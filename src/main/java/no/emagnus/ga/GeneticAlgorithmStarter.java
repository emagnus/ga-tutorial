package no.emagnus.ga;

import no.emagnus.biggestnumber.NumberIndividualGenerator;
import no.emagnus.driving.CarFitnessEvaluator;

public class GeneticAlgorithmStarter {

    public static void main(String[] args) {
        FitnessEvaluator fitnessTester = new CarFitnessEvaluator(true);
        IndividualGenerator individualGenerator = new NumberIndividualGenerator();
        new GeneticAlgorithm(fitnessTester, individualGenerator, true).run();
    }
}
