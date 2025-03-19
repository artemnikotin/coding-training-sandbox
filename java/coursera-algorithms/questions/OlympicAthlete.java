import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Coursera | Algorithms, Part I | Week 06
 * Hashing with wrong hashCode() or equals().
 * Suppose that you implement a data type OlympicAthlete for use in a java.util.HashMap.
 */
public class OlympicAthlete {
    private final String name;
    private final int age;

    public OlympicAthlete(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        if (!override_equals) {
            return super.equals(obj);
        }

        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        OlympicAthlete tha1t = (OlympicAthlete) obj;
        return age == tha1t.age && name.equals(tha1t.name);
    }

    public boolean equals(OlympicAthlete that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        return age == that.age && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        if (!override_hashCode) {
            return super.hashCode();
        }

        return Objects.hash(name, age);
    }

    public static boolean override_equals = true;
    public static boolean override_hashCode = true;

    public static void main(String[] args) {
        OlympicAthlete athlete1 = new OlympicAthlete("Michael", 32);
        OlympicAthlete athlete2 = new OlympicAthlete("Michael", 32);

        // Override both hashCode() and equals()
        OlympicAthlete.override_equals = true;
        OlympicAthlete.override_hashCode = true;
        Map<OlympicAthlete, String> map = new HashMap<>();
        map.put(athlete1, "200 m butterfly");
        map.put(athlete2, "400 m medley");
        System.out.println(map.size()); // Output: 1 (both objects are treated as same keys)

        // Override hashCode() but not equals()
        OlympicAthlete.override_equals = false;
        OlympicAthlete.override_hashCode = true;
        map = new HashMap<>();
        map.put(athlete1, "200 m butterfly");
        map.put(athlete2, "400 m medley");
        System.out.println(map.size()); // Output: 2 (both objects are treated as different keys)

        // Override equals() but not hashCode()
        OlympicAthlete.override_equals = true;
        OlympicAthlete.override_hashCode = false;
        map = new HashMap<>();
        map.put(athlete1, "200 m butterfly");
        System.out.println(map.get(athlete2)); // Output: null (athlete2 is not found)

        // Override equals(OlympicAthlete that) but not equals(Object obj)
        OlympicAthlete.override_equals = false;
        OlympicAthlete.override_hashCode = true;
        map = new HashMap<>();
        map.put(athlete1, "200 m butterfly");
        System.out.println(map.get(athlete2)); // Output: null (athlete2 is not found)
    }
}
