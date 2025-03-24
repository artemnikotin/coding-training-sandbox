package cts.leetcode.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountDaysWithoutMeetingsTest {

    @Test
    void testNoMeetings() {
        assertEquals(10, CountDaysWithoutMeetings.countDays(10, new int[0][0]));
    }

    @Test
    void testMeetings() {
        int[][] meetings = {
                {5, 7},
                {1, 3},
                {9, 10},
        };
        assertEquals(2, CountDaysWithoutMeetings.countDays(10, meetings));
    }

    @Test
    void testOverlappedMeetings() {
        int[][] meetings = {
                {5, 7},
                {1, 3},
                {3, 4},
        };
        assertEquals(3, CountDaysWithoutMeetings.countDays(10, meetings));
    }
}