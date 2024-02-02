package choco

import org.chocosolver.solver.Model
import org.chocosolver.solver.variables.IntVar

int[] values = [1, 5, 10, 15, 17]  // Gem values
int[] weights = [1, 2, 3, 5, 6]    // Weights
int W = 10
int unbounded = 100000

var counts = new IntVar[values.length]
var totalWeight, totalValue
var found = false

new Model('KnapsackProblem').with {
    counts.indices.each {counts[it] = intVar("count$it", 0, W) }
    totalValue = intVar("Total value for capacity $W (unbounded)", 0, unbounded)
    totalWeight = intVar("Total weight taken", 0, W)
    knapsack(counts, totalWeight, totalValue, weights, values).post()
    setObjective(MAXIMIZE, totalValue)

    while(solver.solve()) {
        found = true
        println "$totalValue, $totalWeight, $counts"
    }
}
if (!found) println 'No solution'
