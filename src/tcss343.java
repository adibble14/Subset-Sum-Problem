import java.util.ArrayList;
import java.util.Random;

public class tcss343 {
    public static void main(final String[] args) {}

    public static void BruteForce() {}

    public static void DynamicProgramming() {}

    public static void CleverAlgorithm() {}

    public static void Driver(final int theN, final int theR,
                              final Boolean theV) {
        // Create array of bounded random numbers
        final ArrayList<Integer> s = new ArrayList<>();
        final Random random = new Random();
        for (int i = 0; i < theN; i++) s.add(random.nextInt(theR) + 1);

        // Given true, t is sum of random subset of S
        // Given false, t is greater than sum of set of S
        int t = 0;
        if (theV) {
            // Alternate way
//            final List<Integer> subS = s.subList(0,
//                    1 + random.nextInt(s.size()));
//            t = subS.stream().mapToInt(i -> i).sum();
            final int subS = 1 + random.nextInt(s.size());
            for (int i = 0; i < subS; i++) t += s.get(i);
        }
        else t = s.stream().mapToInt(i -> i).sum() + 1;

        // Quick debugging
        System.out.println(s);
        System.out.println(t);
    }
}
