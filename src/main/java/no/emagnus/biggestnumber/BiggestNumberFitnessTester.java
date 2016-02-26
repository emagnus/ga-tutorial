package no.emagnus.biggestnumber;

import no.emagnus.ga.FitnessTester;

public class BiggestNumberFitnessTester implements FitnessTester<String> {

    public double evaluateFitness(String individual) {
        return Integer.parseInt(individual, 2);
    }
}
