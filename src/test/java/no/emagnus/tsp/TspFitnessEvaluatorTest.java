package no.emagnus.tsp;

import no.emagnus.ga.Individual;
import no.emagnus.tsp.data.TspDataPoint;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class TspFitnessEvaluatorTest {

    private TspFitnessEvaluator fitnessEvaluator;

    @Before
    public void setup() {
        List<TspDataPoint> dataset = Arrays.asList( new TspDataPoint(1,1),
                                                    new TspDataPoint(1,2),
                                                    new TspDataPoint(2,1),
                                                    new TspDataPoint(2,2));

        fitnessEvaluator = new TspFitnessEvaluator(false, dataset);
    }

    @Test
    public void should_assign_fitness_to_all_individuals_in_the_population() throws Exception {
        int[] genotype1 = {1,2,3,0};
        int[] genotype2 = {0,3,1,2};
        List<Individual> population = Arrays.asList(new Individual(genotype1), new Individual(genotype2));

        fitnessEvaluator.evaluateFitness(population);

        for (Individual individual : population) {
            assertThat(individual.getFitness()).isNotZero();
        }
    }
}