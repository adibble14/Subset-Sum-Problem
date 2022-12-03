import java.util.ArrayList;
import java.util.List;
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
            final List<Integer> subS = (List<Integer>) s.clone();
            final int subSize = random.nextInt(s.size() + 1);
            for (int i = 0; i < subSize; i++) {
                t += subS.remove(random.nextInt(subS.size()));
            }
        }
        else t = s.stream().mapToInt(i -> i).sum() + 1;
    }
}
