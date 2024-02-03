package brute

int[] values = [1, 5, 10, 15, 17]  // Gem values
int[] weights = [1, 2, 3, 5, 6]    // Weights
int W = 10
def combos = weights.indices.collect{ [null, it] }.combinations()*.findResults()
combos = combos.findAll{ weights[it].sum() <= W }
def solution = combos.max{ values[it].sum() }
println "Total value for capacity $W = ${values[solution].sum()}"
println "Gems taken: $solution"
println "Gem weights: ${weights[solution]}"
