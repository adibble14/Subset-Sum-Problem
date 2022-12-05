import java.util.*;

public class tcss343 {
    public static void main(String[] args) {
        Driver(5, 5, true);
        Driver(5, 5, false);
    }

    public static ArrayList<Object> BruteForce(int[] seq, int target) {
        final ArrayList<Object> result = new ArrayList<>();
        result.add(BruteForce(seq, seq.length, target, result));
        return result;
    }

    private static boolean BruteForce(int[] seq, int n, int sum,
                                      ArrayList<Object> result) {
        //Our base cases
        //Returns true if sum is equal to 0
        if (sum == 0) {
            return true;
        }
        //Returns true if n is equal to 0
        if (n == 0) {
            return false;
        }
        //Determining if our last element is greater than our sum
        //and if so ignoring it
        if (seq[n - 1] > sum) {
            return BruteForce(seq, n - 1, sum, result);
        }
        //Else check if sum can be obtained by
        //Including last element
        //Excluding last element
        return BruteForce(seq, n - 1, sum, result) || BruteForce(seq, n - 1, sum - seq[n - 1], result);
    }

    public static ArrayList<Object> dynamicProgramming(
            final int[] theS, final int theT) {
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
        if (theT > 0 && a[i][a[i].length - 1]) {
            result.add(true);
            int curr;
            int t = theT;
            while (t > 0) {
                curr = theS[i--];
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

    /**
     * A clever algorithm for solving the Subset Sum Problem
//     * @param theArray the array of integers in the list
     * @param theTarget the target number used for the sum
     * @return an ArrayList of Object arrays containing if the solution has been found (True or False)
     * and the subset that adds up to the target (the empty set if False)
     */
    public static ArrayList<Object> CleverAlgorithm(int[] theArray,
                                                    int theTarget) {

        ArrayList<Object> result = new ArrayList<>();
        //output for the empty subset
        if (theTarget == 0) {
            result.add(true);
            result.add(0);
            return result;
        }

        int middle = theArray.length / 2 + 1;
        int[] l = Arrays.copyOfRange(theArray, 0, middle);
        int[] h = Arrays.copyOfRange(theArray, middle, theArray.length);   //splitting the array into two parts of (nearly) equal size

        ArrayList<Object[]> tableT = findAllSubsets(l, theTarget); //returns all subsets of l that do not exceed theTarget
        if (!tableT.isEmpty()) { //making sure it isn't empty
            if (tableT.get(0)[0] == "TRUE") {  //checking if the solution has been found
                result.add(true);
                result.addAll(Arrays.asList(tableT.get(1)));
                return result;
            }
        }

        ArrayList<Object[]> tableW = findAllSubsets(h, theTarget); //returns all subsets of h that do not exceed theTarget
        if (!tableW.isEmpty()) { //making sure it isn't empty
            if (tableW.get(0)[0] == "TRUE") {  //checking if the solution has been found
                result.add(true);
                result.addAll(Arrays.asList(tableW.get(1)));
                return result;
            }
        }

        //if there are at least one possible subset in each table
        if (!tableT.isEmpty() && !tableW.isEmpty()) {

            //sorting the subsets of tableW by weight in ascending order
            tableW.sort(Comparator.comparingInt(tcss343::getWeight));

            //for each entry in tableT, see if a subset in W combined with the subset in T equals the target, if not there is no solution
            for (Object[] subsetT : tableT) {
                int weightT = getWeight(subsetT);
                for (Object[] subsetW : tableW) {
                    int weightW = getWeight(subsetW);
                    if (weightT + weightW == theTarget) {
                        Object[] combinedSubset = new Object[subsetT.length + subsetW.length];
                        System.arraycopy(subsetT, 0, combinedSubset, 0, subsetT.length);
                        System.arraycopy(subsetW, 0, combinedSubset, subsetT.length, subsetW.length);
                        result.add(true);
                        result.addAll(Arrays.asList(combinedSubset));
                        return result;
                    } else if (weightT + weightW > theTarget) {
                        break;
                    }
                }
            }
        }

        //output for no solution (FALSE)
        result.add(false);
        return result;
    }

    /**
     * returns all subsets of theArray that do not exceed theTarget number
     * or returns "TRUE" and the subset if a solution has been found
     */
    //used https://www.geeksforgeeks.org/power-set/ for the algorithm
    public static ArrayList<Object[]> findAllSubsets(int[] theArray, int theTarget) {

        int numSubsets = (int) Math.pow(2, theArray.length) - 1;
        ArrayList<Integer> temp = new ArrayList<>(); //temporary list
        ArrayList<Object[]> subsets = new ArrayList<>(); //list that contains all the subsets that do not exceed theTarget

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
                Object[] found = {"TRUE"};
                subsets.add(found);
                subsets.add(temp.toArray());
                return subsets;
            } else if (total <= theTarget) {
                subsets.add(temp.toArray());
            }

            temp.clear(); //clearing the temporary list after each subset
        }

        return subsets;
    }

    /**
     * returns the weight of the sublist given
     */
    public static int getWeight(Object[] theSubset) {
        int weight = 0;

        for (Object num : theSubset) {
            weight += (int) num;
        }
        return weight;
    }

    public static void Driver(final int theN, final int theR,
                              final Boolean theV) {
        // Create array of bounded random numbers
        /*final*/ ArrayList<Integer> s = new ArrayList<>();
        final Random r = new Random();
        for (int i = 0; i < theN; i++) s.add(r.nextInt(theR) + 1);

        // Given true, t is sum of random subset of S
        // Given false, t is greater than sum of set of S
        int t = 0;
        if (theV) {
            final List<Integer> subS = (List<Integer>) s.clone();
            final int subSize = r.nextInt(s.size() + 1);
            for (int i = 0; i < subSize; i++)
                t += subS.remove(r.nextInt(subS.size()));
        }
        else t = s.stream().mapToInt(i -> i).sum() + 1;

//        s = new ArrayList<>(Arrays.asList(1, 4, 5, 1, 3));
//        t = 5;

        final int[] s2 =  s.stream().mapToInt(Integer::intValue).toArray();

        System.out.println("Set: " + s);
        System.out.println("Target: " + t);
        System.out.println(BruteForce(s2, t));
        System.out.println(dynamicProgramming(s2, t));
        System.out.println(CleverAlgorithm(s2, t));
        System.out.println();
    }
}
