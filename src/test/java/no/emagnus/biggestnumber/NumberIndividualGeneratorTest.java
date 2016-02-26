package no.emagnus.biggestnumber;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class NumberIndividualGeneratorTest {

    private NumberIndividualGenerator generator = new NumberIndividualGenerator();

    @Test
    public void should_generate_10_character_long_random_bit_string() {
        String s1 = generator.generateSpecimen().getGenotype();
        String s2 = generator.generateSpecimen().getGenotype();

        assertThat(s1).isNotEqualTo(s2);

        assertThat(Integer.parseInt(s1, 2)).isGreaterThan(-1);
        assertThat(Integer.parseInt(s2, 2)).isGreaterThan(-1);
    }

}