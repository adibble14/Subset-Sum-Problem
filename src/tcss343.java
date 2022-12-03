import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class tcss343 {
    public static void main(String[] args){
        int[] array = {2,3,5,7,9};
        int target = 13;
        Object[][] solution = CleverAlgorithm(array, target);

        if(solution[0][0] == "TRUE"){
            System.out.print("A solution has been found!\nThe subset is: ");
            System.out.print("{");
            for(int i = 0; i<solution[1].length; i++){
                System.out.print(solution[1][i]+",");
            }
            System.out.print("} for target number: "+target);
        }else{
            System.out.println("There is no subset in this set that adds up to " + target);
        }
    }

    public static void BruteForce(){}

    public static void DynamicProgramming(){}

    public static Object[][] CleverAlgorithm(int[] theArray, int theTarget){
        double middle = Math.floor(theArray.length/2);
        int[] l = Arrays.copyOfRange(theArray, 0, (int) middle+1);
        int[] h = Arrays.copyOfRange(theArray, (int) middle +1, theArray.length);   //splitting the array into two parts of (nearly) equal size


        Object[][] tableT = findAllSubsets(l, theTarget); //returns all subsets of l that do not exceed theTarget
        if(tableT[0][0]=="TRUE"){  //checking if the solution has been found
            return tableT;
        }
        Object[][] tableW = findAllSubsets(h, theTarget); //returns all subsets of h that do not exceed theTarget
        if(tableW[0][0] == "TRUE"){  //checking if the solution has been found
            return tableW;
        }

        Arrays.sort(tableW, new Comparator<Object[]>() {   //sorting the subsets by weight in ascending order
            @Override
            public int compare(Object[] o1, Object[] o2) {
                if(getWeight(o1) > getWeight(o2)){
                    return 1;
                }else{
                    return -1;
                }
            }
        });

        //for each entry in tableT, see if a subset in W combined with the subset in T equals the target, if not there is no solution
        for (Object[] subsetT: tableT) {
            int weightT = getWeight(subsetT);
            for (Object[] subsetW: tableW) {
                int weightW = getWeight(subsetW);
                if(weightT + weightW == theTarget){
                    Object[] combinedSubset = new Object[subsetT.length + subsetW.length];
                    System.arraycopy(subsetT, 0, combinedSubset, 0, subsetT.length);
                    System.arraycopy(subsetW, 0, combinedSubset, subsetT.length, subsetW.length);
                    Object[][] solution = {{"TRUE"}, combinedSubset};
                    return solution;
                }else if(weightT + weightW > theTarget){
                    break;
                }
            }
        }
        Object[][] solution = {{"FALSE"}, {}};
        return solution;
    }

    /**
     * returns all subsets of theArray that do not exceed theTarget number
     * or returns "TRUE" and the subset if a solution has been found
     */
    //used https://www.geeksforgeeks.org/power-set/ for the algorithm
    public static Object[][] findAllSubsets(int[] theArray, int theTarget){

        int numSubsets = (int)Math.pow(2, theArray.length)-1;
        ArrayList temp = new ArrayList(); //temporary list
        Object[][] subsets = new Object[numSubsets][theArray.length];
        //ArrayList subsets = new ArrayList(); //list that contains all the subsets that do not exceed theTarget

        for(int count = 1; count <= numSubsets; count++) {
            int total = 0;
            for(int j = 0; j < theArray.length; j++) {
                if((count & (1 << j)) > 0) {  // Checks if jth bit in the count is set. If set then print jth element from set
                    total += theArray[j];
                    temp.add(theArray[j]);
                }
            }
            if(total == theTarget){   //if a correct subset has been found
                Object[][] solution = {{"TRUE"}, temp.toArray()};
                return solution;
            } else if(total <= theTarget){
                subsets[count-1] = temp.toArray();
            }else{
                /**
                 * add code to remove spot in the subsets array whenever a total is larger than the target to stop null pointers
                 */

            }
            temp.clear();
        }

        return subsets;
    }
    public static int getWeight(Object[] theSublist){
        int weight = 0;
        /*try {
            for (Object num : theSublist) {
                weight += (int) num;
            }
        }catch (NullPointerException e){
            return -1;
        }*/
        for (Object num : theSublist) {
            weight += (int) num;
        }
        return weight;
    }


    public static void Driver(){}

}



