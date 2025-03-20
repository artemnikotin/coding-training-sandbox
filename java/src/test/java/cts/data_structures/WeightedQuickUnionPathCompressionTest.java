package cts.data_structures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WeightedQuickUnionPathCompressionTest {

    @Test
    public void testInitialState() {
        WeightedQuickUnionPathCompression uf = new WeightedQuickUnionPathCompression(10);
        // Initially, each element should be its own union only.
        for (int i = 0; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                if (i == j) {
                    continue;
                }
                assertFalse(uf.connected(i, j));
            }
            
        }
    }

    @Test
    public void testUnionAndConnected() {
        WeightedQuickUnionPathCompression uf = new WeightedQuickUnionPathCompression(10);

        // Union operations
        uf.union(1, 2);
        uf.union(3, 4);
        uf.union(5, 6);
        uf.union(7, 8);
        uf.union(2, 8);
        uf.union(4, 6);

        // Test connections
        assertTrue(uf.connected(1, 8)); // 1 and 8 should be connected
        assertTrue(uf.connected(3, 5)); // 3 and 5 should be connected
        assertFalse(uf.connected(0, 9)); // 0 and 9 should not be connected
    }
}