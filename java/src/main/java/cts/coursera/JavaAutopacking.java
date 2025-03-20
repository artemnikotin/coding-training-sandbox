package cts.coursera;
/**
 * Coursera | Algorithms, Part I | Week 04
 * Java autoboxing and equals().
 * Consider two double values a and b and their corresponding Double values x and y.
 *  - Find values such that (a == b) is true but x.equals(y) is false.
 *  - Find values such that (a == b) is false but x.equals(y) is true.
 */
public class JavaAutopacking {
    public static void main(String[] args) {
        {
            double a = 0.0;
            double b = -0.0;

            Double x = 0.0;
            Double y = -0.0;

            System.out.println(a == b); // Output: true
            System.out.println(x.equals(y)); // Output: false
        }
        {

            double a = Double.NaN;
            double b = Double.NaN;

            Double x = Double.NaN;
            Double y = Double.NaN;
            System.out.println(a == b); // Output: false
            System.out.println(x.equals(y)); // Output: true
        }
    }
}
