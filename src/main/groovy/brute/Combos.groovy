package brute

int[] values = [1, 5, 10, 15, 17]  // Gem values
int[] weights = [1, 2, 3, 5, 6]    // Weights
int W = 10
var indexCombos = weights.indices.collect{ [null, it] }.combinations()*.findResults()
var validWeight = indexCombos.findAll{ weights[it].sum() <= W }
var best = validWeight.max{ values[it].sum() }
println "Total value for capacity $W = ${values[best].sum()}"
println "Gems taken: $best"
println "Gem weights: ${weights[best]}"
