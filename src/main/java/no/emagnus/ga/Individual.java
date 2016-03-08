package no.emagnus.ga;

public class Individual {

    private int[] genotype;

    private double fitness;

    public Individual(int[] genotype) {
        this.genotype = genotype;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return fitness;
    }

    public int[] getGenotype() {
        return genotype;
    }

    public Individual copy() {
        return new Individual(genotype.clone());
    }

    @Override
    public String toString() {
        return genotype.toString();
    }
}
