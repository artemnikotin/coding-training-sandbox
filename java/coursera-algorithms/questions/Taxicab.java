import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Coursera | Algorithms, Part I | Week 04
 * Taxicab numbers.
 * A taxicab number is an integer that can be expressed as the sum of two cubes of positive integers in two different
 * ways: a^3 + b^3 = c^3 + d^3. For example, 17291729 is the smallest taxicab number: 9^3 + 10^3 = 1^3 + 12^3.
 * Design an algorithm to find all taxicab numbers with a, b, c, and d less than n.
 */
public class Taxicab {
    private final ArrayList<Solution> solutions = new ArrayList<>();

    public Taxicab(int n) {
        PriorityQueue<Expression> pq = new PriorityQueue<>();
        for (int i = 1; i <= n; i++) {
            pq.add(new Expression(i, i));
        }

        var previous = new Expression(0, 0);
        while (!pq.isEmpty()) {
            var current = pq.remove();

            if (current.result.equals(previous.result)) {
                solutions.add(new Solution(previous, current));
            }

            if (current.b <= n) {
                pq.add(new Expression(current.a, current.b + 1));
            }

            previous = current;
        }
    }

    public String toString() {
        var builder = new StringBuilder();
        for (Solution s: solutions) {
            builder.append(s);
            builder.append("\n");
        }
        return builder.toString();
    }

    private static class Expression implements Comparable<Expression> {
        public final int a;
        public final int b;
        public final Integer result;

        private Expression(int a, int b) {
            this.a = a;
            this.b = b;
            result = a * a * a + b * b * b;
        }

        public int compareTo(Expression that) {
            return this.result.compareTo(that.result);
        }
    }

    private static class Solution {
        public final Expression left;
        public final Expression right;

        public Solution(Expression left, Expression right) {
            this.left = left;
            this.right = right;
        }

        public String toString() {
            return left.result + " = " + left.a + "^3 + " + left.b + "^3 = " + right.a + "^3 + " + right.b + "^3";
        }
    }

    public static void main(String[] args) {
        var taxicab = new Taxicab(100);
        System.out.println(taxicab);
    }
}
