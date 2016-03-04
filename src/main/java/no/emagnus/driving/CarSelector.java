package no.emagnus.driving;

import no.emagnus.ga.Individual;
import no.emagnus.ga.Selector;

import java.util.List;
import java.util.Random;

public class CarSelector implements Selector {

    @Override
    public Individual select(List<Individual> population) {
        int randomIndex = new Random().nextInt(population.size());
        return population.get(randomIndex);
    }
}
