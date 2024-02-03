package brute

import groovy.transform.Memoized

int solve(int[] w, int[] v, int W) {
    knapsack(w, v, v.length, W)
}

// knapsack call count 107 plain / 49 memoized
@Memoized
int knapsack(int[] w, int[] v, int n, int W) {
    if (n <= 0) {
        0
    } else if (w[n - 1] > W) {
        knapsack(w, v, n - 1, W)
    } else {
        [knapsack(w, v, n - 1, W),
         v[n - 1] + knapsack(w, v, n - 1, W - w[n - 1])].max()
    }
}

int[] values = [1, 5, 10, 15, 17]  // Gem values
int[] weights = [1, 2, 3, 5, 6]    // Weights
[6, 8, 10].each {
    println "Total value for capacity $it = ${solve(weights, values, it)}"
}

