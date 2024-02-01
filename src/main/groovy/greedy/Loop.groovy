package greedy

int[] values = [1, 5, 10, 15, 17]  // Gem values
int[] weights = [1, 2, 3, 5, 6]    // Weights
var ratios = values.indices.collect { values[it] / weights[it] }.withIndex().sort { -it[0] }
int W = 10
Map<Integer, BigDecimal> taken = [:]
var remaining = W
while (remaining && ratios) {
    var next = ratios.head()
    var index = next[1]
    if (remaining >= weights[index]) {
        taken[index] = 1
        remaining -= weights[index]
    } else {
        taken[index] = remaining / weights[index]
        break
    }
    ratios = ratios.tail()
}
var total = taken.collect{ index, qty -> values[index] * qty }.sum()
println taken
printf 'Total value for capacity %d (with fractions) = %6.3f%n', W, total
