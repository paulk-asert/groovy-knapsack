package dynamic

import static java.lang.Math.max

int solve(int[] v, int[] w, int W) {
    knapsack(new Integer[v.length][W+1], v, w, W, 0)
}

int knapsack(Integer[][] dp, int[] v, int[] w, int W, int n) {
    if (W <= 0 || n >= v.length) {
        return 0
    }
    if (dp[n][W] != null) {
        return dp[n][W]
    }
    int value1 = w[n] > W ? 0 : v[n] + knapsack(dp, v, w, W - w[n], n + 1)
    int value2 = knapsack(dp, v, w, W, n + 1)
    dp[n][W] = max(value1, value2)
}

int[] values = [1, 5, 10, 15, 17]  // Gem values
int[] weights = [1, 2, 3, 5, 6]    // Weights
[6, 8, 10].each {
    println "Total value for capacity $it = ${solve(values, weights, it)}"
}
