package no.emagnus.ga;

import java.util.List;

public interface Selector {

    Individual select(List<Individual> population);
}
