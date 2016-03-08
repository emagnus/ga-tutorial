package no.emagnus.ga;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class IndividualTest {

    @Test
    public void should_copy_the_genotype_and_not_the_fitness_of_an_individual() throws Exception {
        int[] genotype = {0,1};
        Individual theOriginal = new Individual(genotype);
        theOriginal.setFitness(23.4);

        Individual theCopy = theOriginal.copy();

        assertThat(theCopy.getFitness()).isZero();
        assertThat(theCopy.getGenotype()).isEqualTo(theOriginal.getGenotype());
        assertThat(theCopy.getGenotype() == theOriginal.getGenotype()).isFalse();
    }
}