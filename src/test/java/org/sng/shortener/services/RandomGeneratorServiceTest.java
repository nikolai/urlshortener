package org.sng.shortener.services;

import junit.framework.TestCase;
import org.assertj.core.api.Assertions;

public class RandomGeneratorServiceTest extends TestCase {

    public void testGenerateString() {
        int desiredSize = 5;
        String resultString = new RandomGeneratorService().generateString(desiredSize);
        Assertions.assertThat(resultString).hasSize(desiredSize);
    }
}