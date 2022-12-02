import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class tcss343 {
    public static void main(String[] args){
        int[] array = {2,3,5,7,9};
        int target = 13;
        ArrayList output = new ArrayList();
        output = CleverAlgorithm(array, target);

       /* Object[] solution = output.toArray();
        if(solution[0] == "TRUE"){
            int[] subset = (int[]) solution[1];
            System.out.print("A solution has been found!\nThe subset is: ");
            System.out.println("{"+subset[0] + "," + subset[1] + "}");
        }else{
            System.out.println("There is no subset in this set that adds up to " + target);
        }*/
    }

    public static void BruteForce(){}

    public static void DynamicProgramming(){}

    public static ArrayList CleverAlgorithm(int[] theArray, int theTarget){
        double middle = Math.floor(theArray.length/2);
        int[] l = Arrays.copyOfRange(theArray, 0, (int) middle+1);
        int[] h = Arrays.copyOfRange(theArray, (int) middle +1, theArray.length);   //splitting the array into two parts of (nearly) equal size

        ArrayList tableT = findAllSubsets(l, theTarget);       //returns all subsets of l that do not exceed theTarget
        if(tableT.get(0)=="TRUE"){  //checking if the solution has been found
           return tableT;
        }

        ArrayList tableW = findAllSubsets(h, theTarget);       //returns all subsets of h that do not exceed theTarget
        if(tableW.get(0)=="TRUE"){  //checking if the solution has been found
            return tableW;
        }




        return null;
    }

    /**
     * returns all subsets of theArray that do not exceed theTarget number
     */
    //used https://www.geeksforgeeks.org/power-set/ for the algorithm
    public static ArrayList findAllSubsets(int[] theArray, int theTarget){

        int numSubsets = (int)Math.pow(2, theArray.length)-1;
        ArrayList temp = new ArrayList(); //temporary list
        ArrayList subsets = new ArrayList(); //list that contains all the subsets that do not exceed theTarget

        for(int count = 1; count <= numSubsets; count++) {
            int total = 0;
            for(int j = 0; j < theArray.length; j++) {
                if((count & (1 << j)) > 0) {  // Checks if jth bit in the count is set. If set then print jth element from set
                    total += theArray[j];
                    temp.add(theArray[j]);
                }
            }
                if(total == theTarget){   //if a correct subset has been found
                    subsets.clear();
                    subsets.add("TRUE");
                    subsets.add(temp.toArray());
                    return subsets;
                } else if(total <= theTarget){
                    subsets.add(temp.toArray());    //adding the subset to the array
                }
                temp.clear();
        }

        return subsets;
    }

    public static void Driver(){}

}


