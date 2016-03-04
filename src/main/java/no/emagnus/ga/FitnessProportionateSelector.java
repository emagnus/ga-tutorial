package no.emagnus.ga;

import java.util.List;
import java.util.Random;

public class FitnessProportionateSelector implements Selector {

    @Override
    public Individual select(List<Individual> population) {
        // TODO Well this sure isn't fitness proportionate selection ...
        int randomIndex = new Random().nextInt(population.size());
        return population.get(randomIndex);
    }
}
