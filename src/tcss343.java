import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class tcss343 {
    public static void main(String[] args) {
        int[] array = {2, 3, 5, 9, 7};
        int target = 1;

        ArrayList<Object[]> solution = CleverAlgorithm(array, target);  //calling the clever algorithm

        //output
        if (solution.get(0)[0] == "TRUE") {
            System.out.print("A solution has been found!\nThe subset is: ");
            System.out.print("{");
            for (int i = 0; i < solution.get(1).length; i++) {
                System.out.print(solution.get(1)[i] + ",");
            }
            System.out.print("} for target number: " + target);
        } else {
            System.out.println("There is no subset in this set that adds up to " + target);
        }
    }

    public static void BruteForce() {
    }

    public static void DynamicProgramming() {
    }

    /**
     * A clever algorithm for solving the Subset Sum Problem
     * @param theArray the array of integers in the list
     * @param theTarget the target number used for the sum
     * @return an ArrayList of Object arrays containing if the solution has been found (True or False)
     * and the subset that adds up to the target (the empty set if False)
     */
    public static ArrayList<Object[]> CleverAlgorithm(int[] theArray, int theTarget) {

        double middle = Math.floor(theArray.length / 2);
        int[] l = Arrays.copyOfRange(theArray, 0, (int) middle + 1);
        int[] h = Arrays.copyOfRange(theArray, (int) middle + 1, theArray.length);   //splitting the array into two parts of (nearly) equal size


        ArrayList<Object[]> tableT = findAllSubsets(l, theTarget); //returns all subsets of l that do not exceed theTarget
        if (!tableT.isEmpty()) { //making sure it isn't empty
            if (tableT.get(0)[0] == "TRUE") {  //checking if the solution has been found
                return tableT;
            }
        }

        ArrayList<Object[]> tableW = findAllSubsets(h, theTarget); //returns all subsets of h that do not exceed theTarget
        if (!tableW.isEmpty()) { //making sure it isn't empty
            if (tableW.get(0)[0] == "TRUE") {  //checking if the solution has been found
                return tableW;
            }
        }

        //if there are at least one possible subset in each table
        if (!tableT.isEmpty() && !tableW.isEmpty()) {

            Collections.sort(tableW, new Comparator<Object[]>() {   //sorting the subsets of tableW by weight in ascending order
                @Override
                public int compare(Object[] o1, Object[] o2) {
                    if (getWeight(o1) > getWeight(o2)) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });

            //for each entry in tableT, see if a subset in W combined with the subset in T equals the target, if not there is no solution
            for (Object[] subsetT : tableT) {
                int weightT = getWeight(subsetT);
                for (Object[] subsetW : tableW) {
                    int weightW = getWeight(subsetW);
                    if (weightT + weightW == theTarget) {
                        Object[] combinedSubset = new Object[subsetT.length + subsetW.length];
                        System.arraycopy(subsetT, 0, combinedSubset, 0, subsetT.length);
                        System.arraycopy(subsetW, 0, combinedSubset, subsetT.length, subsetW.length);
                        //Object[][] solution = {{"TRUE"}, combinedSubset};
                        ArrayList<Object[]> solution = new ArrayList<Object[]>();
                        Object[] found = {"TRUE"};
                        solution.add(found);
                        solution.add(combinedSubset);
                        return solution;
                    } else if (weightT + weightW > theTarget) {
                        break;
                    }
                }
            }
        }

        //output for no solution (FALSE and the empty subset)
        ArrayList<Object[]> solution = new ArrayList<Object[]>();
        Object[] answer = {"FALSE"};
        solution.add(answer);
        Object[] emptySubset = {};
        solution.add(emptySubset);
        return solution;
    }

    /**
     * returns all subsets of theArray that do not exceed theTarget number
     * or returns "TRUE" and the subset if a solution has been found
     */
    //used https://www.geeksforgeeks.org/power-set/ for the algorithm
    public static ArrayList<Object[]> findAllSubsets(int[] theArray, int theTarget) {

        int numSubsets = (int) Math.pow(2, theArray.length) - 1;
        ArrayList temp = new ArrayList(); //temporary list
        ArrayList<Object[]> subsets = new ArrayList<Object[]>(); //list that contains all the subsets that do not exceed theTarget

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

    public static void Driver() {}

}



