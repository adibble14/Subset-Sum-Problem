import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class tcss343 {
    public static void main(final String[] args) {
        // TESTING
        Driver(5,5,true);
        Driver(5,5,false);
    }

    public static void BruteForce() {}

    public static ArrayList<Object> dynamicProgramming(
            final ArrayList<Integer> theS, final int theT) {
        boolean[][] a = new boolean[theS.size()][theT + 1];
        // first column
        for (int i = 0; i < a.length; i++) a[i][0] = true;
        // first row
        for (int j = 1; j < a[0].length; j++) a[0][j] = theS.get(0) == j;
        // rest of rows and columns
        for (int i = 1; i < a.length; i++) {
            for (int j = 1; j < a[i].length; j++) {
                final int sI = theS.get(i);
                final int prevRow = i - 1;
                if (j > sI || j == sI) {
                    a[i][j] = a[prevRow][j] || a[prevRow][j - theS.get(i)];
                } else {
                    a[i][j] = a[prevRow][j];
                }
            }
        }

        // TESTING
        printArray(a);
        System.out.println();

        // Recover subset
        final ArrayList<Object> result = new ArrayList<>();
        int i = a.length - 1;
        if (theT > 0 && a[i][a[i].length - 1]) {
            result.add(true);
            int curr;
            int t = theT;
            while (t > 0) {
                curr = theS.get(i--);
                int bound = t - curr;
                if (bound == 0 || (bound > 0 && a[i][bound])) {
                    result.add(curr);
                    t -= curr;
                }
            }
        } else if (theT == 0) {
            result.add(true);
            result.add(0);
        } else result.add(false);
        return result;
    }

    public static void printArray(final boolean[][] theArray) {
        for (boolean[] booleans : theArray) {
            for (boolean aBoolean : booleans) System.out.print(aBoolean + " ");
            System.out.println();
        }
    }

    public static void CleverAlgorithm() {}

    public static void Driver(final int theN, final int theR,
                              final Boolean theV) {
        // Create array of bounded random numbers
        /*final*/ ArrayList<Integer> s = new ArrayList<>();
        final Random random = new Random();
        for (int i = 0; i < theN; i++) s.add(random.nextInt(theR) + 1);
        // TESTING
//        s = new ArrayList<>(Arrays.asList(2, 3, 5, 7, 9));
//        s = new ArrayList<>(Arrays.asList(9,7,5,3,2));
//        s = new ArrayList<>(Arrays.asList(5, 1, 1, 1, 5));
        System.out.println(s);


        // Given true, t is sum of random subset of S
        // Given false, t is greater than sum of set of S
        int t = 0;
        if (theV) {
            final List<Integer> subS = (List<Integer>) s.clone();
            final int subSize = random.nextInt(s.size() + 1);
            for (int i = 0; i < subSize; i++) {
                t += subS.remove(random.nextInt(subS.size()));
            }
            // TESTING
            System.out.println(subS);
        }
        else t = s.stream().mapToInt(i -> i).sum() + 1;

        // TESTING
//        t = 12;
//        t = 11;

        // TESTING
        System.out.println(t);

        System.out.println(dynamicProgramming(s, t));
    }
}
