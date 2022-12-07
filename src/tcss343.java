import java.util.*;

public class tcss343 {
    public static void main(String[] args) {
        // Warning: r >= n only else infinite
        Driver(25, 1000, true);
        //Driver(5, 1000, false);
    }

    public static ArrayList<Object> BruteForce(int[] seq, int target) {
        final ArrayList<Object> result = new ArrayList<>();
        final ArrayList<Integer> subset = new ArrayList<>();
        result.add(BruteForce(seq.length, seq, target, subset));
        result.add(subset);
        return result;
    }

    /**
     * iteratively brute force find the first possible solution to subset sum
     * https://people.sc.fsu.edu/~jburkardt/cpp_src/subset_sum_brute/subset_sum_brute.cpp
     *
     * @author (original) John Burkardt
     * @param n the number of elements
     * @param s the sequence
     * @param t the target sum
     * @param ss the subset if found
     * @return boolean representing whether subset was found or not
     */
    private static boolean BruteForce(int n, int[] s, int t,
                                      ArrayList<Integer> ss) {
        boolean found = false;
        double max = Math.pow(2, n);
        for (int i = 0; i < max; i++) {
            int[] choice = new int[n];
            String bin = Integer.toBinaryString(i);
            int l = bin.length() - 1;
            for (int j = 0; j < n; j++) {
                if (l < 0) choice[j] = 0;
                else choice[j] = Character.getNumericValue(bin.charAt(l--));
            }
            int sum = 0;
            for (int k = 0; k < n; k++) sum += choice[k] * s[k];
            if (sum == t) {
                found = true;
                for (int j = 0; j < choice.length; j++) if (choice[j] == 1) ss.add(s[j]);
                break;
            }
        }
        return found;
    }

    public static ArrayList<Object> dynamicProgramming(final int[] theS,
                                                       final int theT) {
        final boolean[][] a = new boolean[theS.length][theT + 1];
        // first column
        for (int i = 0; i < a.length; i++) a[i][0] = true;
        // first row
        for (int j = 1; j < a[0].length; j++) a[0][j] = theS[0] == j;
        // rest of rows and columns
        for (int i = 1; i < a.length; i++) {
            for (int j = 1; j < a[i].length; j++) {
                final int sI = theS[i];
                final int prevRow = i - 1;
                if (j < sI) a[i][j] = a[prevRow][j];
                else a[i][j] = a[prevRow][j] || a[prevRow][j - sI];
            }
        }
        // Recover subset
        final ArrayList<Object> result = new ArrayList<>();
        int i = a.length - 1;
        if (a[i][a[i].length - 1]) {
            final ArrayList<Integer> subset = new ArrayList<>();
            result.add(true);
            int curr;
            int t = theT;
            while (t > 0) {
                curr = theS[i--];
                int bound = t - curr;
                if (bound == 0 || (bound > 0 && a[i][bound])) {
                    subset.add(curr);
                    t -= curr;
                }
            }
            result.add(subset);
        } else if (theT == 0) {
            result.add(true);
            result.add(new ArrayList<Integer>());
        } else {
            result.add(false);
            result.add(new ArrayList<Integer>());
        }
        return result;
    }

    /**
     * A clever algorithm for solving the Subset Sum Problem
     * @param theArray the array of integers in the list
     * @param theTarget the target number used for the sum
     * @return an ArrayList of Object arrays containing if the solution has been found (True or False)
     * and the subset that adds up to the target (the empty set if False)
     */
    public static ArrayList<Object> CleverAlgorithm(int[] theArray,
                                                    int theTarget) {
        ArrayList<Object> result = new ArrayList<>();

        int middle = theArray.length / 2 + 1;
        int[] l = Arrays.copyOfRange(theArray, 0, middle);
        int[] h = Arrays.copyOfRange(theArray, middle, theArray.length);   //splitting the array into two parts of (nearly) equal size

        ArrayList<Object> tableT = findAllSubsets(l, theTarget); //returns all subsets of l that do not exceed theTarget
        if (!tableT.isEmpty() && tableT.get(0).equals(true)) return tableT;

        ArrayList<Object> tableW = findAllSubsets(h, theTarget); //returns all subsets of h that do not exceed theTarget
        if (!tableW.isEmpty() && tableW.get(0).equals(true)) return tableW;

        //if there are at least one possible subset in each table
        if (!tableT.isEmpty() && !tableW.isEmpty()) {

            //sorting the subsets of tableW by weight in ascending order
            tableW.sort(Comparator.comparingInt(tcss343::getWeight));

            //for each entry in tableT, see if a subset in W combined with the subset in T equals the target, if not there is no solution
            for (Object subsetT : tableT) {
                int weightT = getWeight(subsetT);
                for (Object subsetW : tableW) {
                    int weightW = getWeight(subsetW);
                    if (weightT + weightW == theTarget) {
                        ArrayList<Integer> combinedSubset = new ArrayList<>((ArrayList<Integer>) subsetT);
                        combinedSubset.addAll((ArrayList<Integer>) subsetW);
                        result.add(true);
                        result.add(combinedSubset);
                        return result;
                    } else if (weightT + weightW > theTarget) {
                        break;
                    }
                }
            }
        }

        if (theTarget == 0) result.add(true);
        else result.add(false);
        result.add(new ArrayList<Integer>());
        return result;
    }

    /**
     * returns all subsets of theArray that do not exceed theTarget number
     * or returns "TRUE" and the subset if a solution has been found
     */
    //used https://www.geeksforgeeks.org/power-set/ for the algorithm
    public static ArrayList<Object> findAllSubsets(int[] theArray,
                                                   int theTarget) {
        double numSubsets = Math.pow(2, theArray.length) - 1;
        ArrayList<Integer> temp = new ArrayList<>(); //temporary list
        ArrayList<Object> subsets = new ArrayList<>(); //list that contains all the subsets that do not exceed theTarget

        for (int count = 1; count <= numSubsets; count++) {
            int total = 0;
            for (int j = 0; j < theArray.length; j++) {
                if ((count & (1 << j)) > 0) {  // Checks if jth bit in the count is set. If set then print jth element from set
                    total += theArray[j];
                    temp.add(theArray[j]);
                }
            }
            if (total == theTarget) {   //if a correct subset has been found
                subsets.clear();
                subsets.add(true);
                subsets.add(temp);
                return subsets;
            } else if (total <= theTarget) {
                subsets.add(temp);
            }

            temp = new ArrayList<>(); //clearing the temporary list after each subset
        }

        return subsets;
    }

    /**
     * returns the weight of the sublist given
     */
    public static int getWeight(Object theSubset) {
        ArrayList<Integer> subset = (ArrayList<Integer>) theSubset;
        return subset.stream().mapToInt(i -> i).sum();
    }

    /**
     * a method that tests each algorithm with random input
     * @param theN n random elements
     * @param theR the elements are in range from 1 to r
     * @param theV if the target sum will be met
     */
    public static void Driver(final int theN, final int theR,
                              final Boolean theV) {
        // Create array of bounded random numbers, no repeats
        final Random r = new Random();
        final int[] s = new int[theN];
        final Set<Integer> d = new HashSet<>();
        while (d.size() < theN) d.add(r.nextInt(theR) + 1); // prevent dupes
        int y = 0;
        for (Integer x : d) s[y++] = x;

        // Given true, t is sum of random subset of S
        // Given false, t is greater than sum of set of S
        int t = 0;
        if (theV) {
            final ArrayList<Integer> subS = new ArrayList<>(d);
            final int subSize = r.nextInt(s.length + 1);
            for (int i = 0; i < subSize; i++)
                t += subS.remove(r.nextInt(subS.size()));
        } else t = Arrays.stream(s).sum() + 1;

        //output
        System.out.println("Set: " + Arrays.toString(s) + "  Target (t): " + t + "  Number of Elements (n): " + theN + "  Range of Values: 1-"+theR);System.out.println();

        char theta = '\u0398';
        long start = System.currentTimeMillis();
        ArrayList<Object> bruteForce = BruteForce(s, t);
        long end = System.currentTimeMillis();
        System.out.println("Brute Force:");System.out.println(bruteForce);
        System.out.print("Execution time in milliseconds: ");System.out.print(end-start + ",");
        System.out.println("  Table space: " + theta+"(n(2^n))");System.out.println();

        long start2 = System.currentTimeMillis();
        ArrayList<Object> dynamicProgramming = dynamicProgramming(s, t);
        long end2 = System.currentTimeMillis();
        System.out.println("Dynamic Programming:");System.out.println(dynamicProgramming);
        System.out.print("Execution time in milliseconds: ");System.out.print(end2-start2 + ",");
        System.out.println("  Table space: " + theta+"(n*(t+2))");System.out.println();

        long start3 = System.currentTimeMillis();
        ArrayList<Object> cleverAlgorithm = CleverAlgorithm(s, t);
        long end3 = System.currentTimeMillis();
        System.out.println("Clever Algorithm:");System.out.println(cleverAlgorithm);
        System.out.print("Execution time in milliseconds: ");System.out.print(end3-start3 + ",");
        System.out.println("  Table space: " + theta+"(2^(n+1))");System.out.println();

        System.out.println();
    }
}