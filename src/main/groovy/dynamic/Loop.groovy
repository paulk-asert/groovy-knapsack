package dynamic

import static java.lang.Math.max

int[] values = [1, 5, 10, 15, 17]  // Gem values
int[] weights = [1, 2, 3, 5, 6]    // Weights
int W = 10
int N = values.length

int[][] dp = new int[N + 1][W + 1]
boolean[][] sol = new boolean[N + 1][W + 1]

for (int n = 1; n <= N; n++) {
    for (int w = 1; w <= W; w++) {
        int skipN = dp[n - 1][w]
        int tryN = weights[n - 1] > w ? 0 : values[n - 1] + dp[n - 1][w - weights[n - 1]]
        dp[n][w] = max(skipN, tryN)
        sol[n][w] = tryN > skipN
    }
}

println "Total value for capacity $W = ${dp[N][W]}"

def taken = []
for (int i = N, j = W; j > 0; i--) {
    if (sol[i][j]) {
        taken << i - 1
        j -= weights[i - 1]
    }
}
println "Gems taken: $taken"
