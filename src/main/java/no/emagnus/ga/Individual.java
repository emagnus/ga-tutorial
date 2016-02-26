package no.emagnus.ga;

public class Individual<T> {

    private T genotype;

    private double fitness;

    public Individual(T genotype) {
        this.genotype = genotype;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return fitness;
    }

    public T getGenotype() {
        return genotype;
    }

    @Override
    public String toString() {
        return genotype.toString();
    }
}
