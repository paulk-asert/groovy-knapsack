package choco

import org.chocosolver.solver.Model
import org.chocosolver.solver.variables.IntVar

import static org.chocosolver.solver.search.strategy.Search.inputOrderLBSearch
import static org.chocosolver.solver.search.strategy.Search.inputOrderUBSearch

int[] values = [1, 5, 10, 15, 17]  // Gem values
int[] weights = [1, 2, 3, 5, 6]    // Weights
int W = 6
int unbounded = 100000

var counts = new IntVar[values.length]
var total

var found = new Model('KnapsackProblem').with {
    counts.indices.each {counts[it] = intVar("count$it", 0, W) }
    scalar(counts, weights, '<=', W).post()
    total = intVar('Total value', 0, unbounded)
    scalar(counts, values, '=', total).post()
    setObjective(Model.MAXIMIZE, total)
    // top-down search
    solver.setSearch(inputOrderUBSearch(total), inputOrderLBSearch(counts))
//    solver.showDecisions()

    solver.solve()
}
if (found) {
    println counts
    println total
} else {
    println 'No solution'
}
