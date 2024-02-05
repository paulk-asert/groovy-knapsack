package brute

int[] values = [1, 5, 10, 15, 17]  // Gem values
int[] weights = [1, 2, 3, 5, 6]    // Weights
int W = 10
var totalValue = { it*.v.sum() }
var withinLimit = { it*.w.sum() <= W }
var asTriple = { [i:it, w:weights[it], v:values[it]] }
var best = weights
    .indices
    .collect(asTriple)
    .subsequences()
    .findAll(withinLimit)
    .max(totalValue)
println "Total value for capacity $W = ${totalValue(best)}"
println "Gems taken: ${best*.i}"
println "Gem weights: ${best*.w}"
