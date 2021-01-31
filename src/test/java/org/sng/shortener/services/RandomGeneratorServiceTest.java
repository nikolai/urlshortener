package org.sng.shortener.services;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class RandomGeneratorServiceTest {
    @Test
    public void testGenerateString() {
        int desiredSize = 5;
        String resultString = new RandomGeneratorService().generateString(desiredSize);
        Assertions.assertThat(resultString).hasSize(desiredSize);
    }
}
