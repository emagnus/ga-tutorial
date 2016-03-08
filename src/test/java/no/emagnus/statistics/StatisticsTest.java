package no.emagnus.statistics;

import no.emagnus.ga.Individual;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.fest.assertions.api.Assertions.assertThat;

public class StatisticsTest {

    private Statistics statistics;

    @Before
    public void setup() {
        statistics = new Statistics(2);
    }

    @Test
    public void should_record_max_fitness_value_per_generation() throws Exception {
        Individual individual1 = new Individual(new int[2]);
        Individual individual2 = new Individual(new int[2]);
        Individual individual3 = new Individual(new int[2]);
        individual1.setFitness(1);
        individual2.setFitness(2);
        individual3.setFitness(3);

        statistics.recordMaxAndAvg(0, Arrays.asList(individual1, individual2));
        statistics.recordMaxAndAvg(1, Arrays.asList(individual2, individual3));

        assertThat(statistics.getMaxFitness()).hasSize(2);
        assertThat(statistics.getMaxFitness()[0]).isEqualTo(2);
        assertThat(statistics.getMaxFitness()[1]).isEqualTo(3);
    }

    @Test
    public void should_record_average_fitness_value_per_generation() throws Exception {
        Individual individual1 = new Individual(new int[2]);
        Individual individual2 = new Individual(new int[2]);
        Individual individual3 = new Individual(new int[2]);
        individual1.setFitness(2);
        individual2.setFitness(5);
        individual3.setFitness(3);

        statistics.recordMaxAndAvg(0, Arrays.asList(individual1, individual2));
        statistics.recordMaxAndAvg(1, Arrays.asList(individual2, individual3));

        assertThat(statistics.getAvgFitness()).hasSize(2);
        assertThat(statistics.getAvgFitness()[0]).isEqualTo(3.5);
        assertThat(statistics.getAvgFitness()[1]).isEqualTo(4);
    }
}