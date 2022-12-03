public class tcss343 {

    public static void main(String[] args) {
        int[] set = {3, 34, 4, 12, 5, 2};
        int n = set.length;
        int target = 9;

        if (BruteForce(set, n, target)) {
            System.out.println("Found subset with given sum");
        } else {
            System.out.println("No subset with given sum");
        }
    }

    public static boolean BruteForce(int[] target, int n, int sum) {
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
        if (target[n - 1] > sum) {
            return BruteForce(target, n - 1, sum);
        }
        //Else check if sum can be obtained by
        //Including last element
        //Excluding last element
        return BruteForce(target, n - 1, sum) || BruteForce(target, n - 1, sum - target[n - 1]);
    }
    public static void DynamicProgramming () {
    }
    public static void CleverAlgorithm () {
    }
    public static void Driver () {
    }
}
