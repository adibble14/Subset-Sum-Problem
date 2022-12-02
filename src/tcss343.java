public class tcss343 {
    
    public static void main(String[] args) {
        int[] set = {3, 34, 4, 12, 5, 2};
        int n = set.length;
        int target = 9;
        if (BruteForce(set, n, target))
            System.out.println("Found subset with given sum");
        else
            System.out.println("No subset with given sum");
    }
    public static boolean BruteForce(int[] target, int n, int sum) {
        if (sum == 0) {
            return true;
        }
        if (n == 0) {
            return false;
        }
        if (target[n-1] > sum) {
            return BruteForce(target, n-1, sum);
        }
        return BruteForce(target, n-1, sum) || BruteForce(target, n-1, sum-target[n-1]);
    }

    public static void DynamicProgramming(){}

    public static void CleverAlgorithm(){}

    public static void Driver(){}

}
