package cts.leetcode.array;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Leetcode | Medium | 3169. Count Days Without Meetings
 * You are given a positive integer days representing the total number of days an employee is available for work
 * (starting from day 1). You are also given a 2D array meetings of size n where, meetings[i] = [start_i, end_i]
 * represents the starting and ending days of meeting i (inclusive).
 * Return the count of days when the employee is available for work but no meetings are scheduled.
 * Note: The meetings may overlap.
 */
public class CountDaysWithoutMeetings {
    public static int countDays(int days, int[][] meetings) {
        Arrays.sort(meetings, Comparator.comparingInt(a -> a[0]));

        int last_end = 0;
        for (int[] m: meetings) {
            int start = Math.max(m[0], last_end + 1);
            int length = m[1] - start + 1;
            days -= Math.max(length, 0);
            last_end = Math.max(last_end, m[1]);
        }
        return days;
    }
}
