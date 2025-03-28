package cts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathTest {
    @Test
    void testHalfIndexDetection() {
        for (int low = 0; low < 10; low++) {
            for (int high = low; high < low + 10; high++) {
                int finalLow = low;
                int finalHigh = high;
                assertEquals(low + (high - low) / 2, (low + high) / 2, () -> String.format(
                        "Low: %d, High: %d", finalLow, finalHigh)
                );
            }
        }
    }
}