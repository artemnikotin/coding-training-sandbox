import java.util.Arrays;

/**
 * Coursera | Algorithms, Part I | Week 01
 * 3-SUM in quadratic time.
 * Design an algorithm for the 3-SUM problem that takes time proportional to n^2 in the worst case. You may assume that
 * you can sort the n integers in time proportional to n^2 or better.
 */
public class ThreeSum {
    public static int count(int[] nums) {
        final int n = nums.length;
        if (n == 0) {
            return 0;
        }
        int count = 0;
        Arrays.sort(nums);
        int i = 0;
        while (i < n - 2) {
            if (nums[i] > 0) {
                break;
            }
            if (i > 0 && nums[i - 1] == nums[i]) {
                i++;
                continue;
            }
            int j = i + 1;
            int k = n - 1;

            while (j < k) {
                int total = nums[i] + nums[j] + nums[k];
                if (total > 0) {
                    do {
                        k--;
                    } while (j < k && nums[k] == nums[k + 1]);

                } else if (total < 0) {
                    do {
                        j++;
                    } while (j < k && nums[j - 1] == nums[j]);
                } else {
                    count++;
                    do {
                        j++;
                    } while (j < k && nums[j - 1] == nums[j]);
                }
            }
            i++;
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(ThreeSum.count(new int[]{-5, 2, 4, 6, -8})); // Output: 1
        System.out.println(ThreeSum.count(new int[]{-5, 2, 4, 6})); // Output: 0
    }
}
