package no.emagnus.ga;

public class RunConfig {

    public int POPULATION_SIZE = 1;
    public int NUMBER_OF_GENERATIONS = 1;
    public double MUTATION_RATE = 0;
    public double CROSSOVER_RATE = 0;
    public int ELITISM = 0;

    public FitnessEvaluator fitnessEvaluator;
    public IndividualGenerator individualGenerator;
    public Selector selector;
    public Recombinator combiner;
    public Mutator mutator;

    public boolean visualizeStats;
}
