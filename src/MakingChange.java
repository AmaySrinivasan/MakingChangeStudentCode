/**
 * The class Making Change solves a classic problem:
 * given a set of coins, how many ways can you make change for a target amount?
 *
 * @author Zach Blick
 * @author Amay Srinivasan
 */

public class MakingChange {

    public static long countWays(int target, int[] coins) {
        // return countWaysMemo(target, coins);
        return countWaysTab(target, coins);
    }

    private static long countWaysMemo(int target, int[] coins) {
        int n = coins.length;
        // Initialization of the memoization table with -1s to indicate that each value is uncomputed as of now
        long[][] memo = new long[target + 1][n];
        for (int amount = 0; amount <= target; amount++) {
            for (int i = 0; i < n; i++) {
                memo[amount][i] = -1;
            }
        }
        // Start the recursion, considering all the coins
        return memoHelper(target, n - 1, coins, memo);
    }

    private static long memoHelper(int amount, int index, int[] coins, long[][] memo) {
        // The base case, where it is the exact amount
        if (amount == 0) {
            return 1;
        }
        // The invalid cases where there is a negative amount or no coins are left
        if (amount < 0 || index < 0) {
            return 0;
        }
        // Returns the stored result if this section of the problem was already solved (it would be -1 if unsolved)
        if (memo[amount][index] != -1) {
            return memo[amount][index];
        }
        // Counts combinations by either including or excluding the current coin denomination
        long include = memoHelper(amount - coins[index], index, coins, memo);
        long exclude = memoHelper(amount, index - 1, coins, memo);
        // Stores and returns the total number of ways (both include and exclude paths)
        memo[amount][index] = include + exclude;
        return memo[amount][index];
    }

    private static long countWaysTab(int target, int[] coins) {
        int n = coins.length;
        // 2d table where [row][col] values represent num combinations that form amount 'row' using first 'col' coins
        long[][] ways = new long[target + 1][n + 1];
        // The base case with the one way to make the amount 0, by choosing no coins
        for (int i = 0; i <= n; i++) {
            ways[0][i] = 1;
        }
        // Builds the table
        for (int amount = 1; amount <= target; amount++) {
            for (int i = 1; i <= n; i++) {
                // The ways to form the amount without using the current coin
                ways[amount][i] = ways[amount][i - 1];
                // If possible, the ways to form the amount including the current coin
                if (amount >= coins[i - 1]) {
                    ways[amount][i] += ways[amount - coins[i - 1]][i];
                }
            }
        }
        // Returns our final answer of the ways to form the target using all coins
        return ways[target][n];
    }
}