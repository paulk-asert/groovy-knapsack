package choco

import org.chocosolver.solver.Model
import org.chocosolver.solver.variables.IntVar

int[] values = [1, 5, 10, 15, 17]  // Gem values
int[] weights = [1, 2, 3, 5, 6]    // Weights
int W = 10
int unbounded = 100000

var counts = new IntVar[values.length]
var total
var found = false

new Model('KnapsackProblem').with {
    counts.indices.each { counts[it] = intVar("count$it", 0, W) }
    scalar(counts, weights, '<=', W).post()
    total = intVar("Total value for capacity $W (unbounded)", 0, unbounded)
    scalar(counts, values, '=', total).post()
    setObjective(MAXIMIZE, total)

    while (solver.solve()) {
        found = true
        println "$total, $counts"
    }
}
if (!found) println 'No solution'
