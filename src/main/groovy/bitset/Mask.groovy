package bitset

int[] values = [1, 5, 10, 15, 17]  // Gem values
int[] weights = [1, 2, 3, 5, 6]    // Weights
int W = 10
var N = weights.size()
var maskW = 1L << W
var nums = 0L..<(1L << N)
var totalValue = nums
    .collect{ BitSet.valueOf(it) }
    .findAll{ mask -> mask.stream().reduce(1) {a, b -> a | a << weights[b] } & maskW }
    .findAll{ mask -> mask.stream().map(idx -> weights[idx]).reduce(0, Integer::sum) <= W }
    .collect{ mask -> nums.indices.sum{ idx -> mask[idx] ? values[idx] : 0 } }
    .max()
println "Total value for capacity $W = $totalValue"
