package no.emagnus.ga;

public class Individual {

    private static long individualId = 0;

    private long id = individualId++;

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

    @Override
    public String toString() {
        return genotype.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Individual that = (Individual) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
