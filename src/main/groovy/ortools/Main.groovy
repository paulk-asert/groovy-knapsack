package ortools

import com.google.ortools.Loader
import com.google.ortools.algorithms.KnapsackSolver

import static com.google.ortools.algorithms.KnapsackSolver.SolverType.KNAPSACK_MULTIDIMENSION_BRANCH_AND_BOUND_SOLVER

Loader.loadNativeLibraries()
var solver = new KnapsackSolver(KNAPSACK_MULTIDIMENSION_BRANCH_AND_BOUND_SOLVER, "knapsack")

long[] values = [1, 5, 10, 15, 17]
long[][] weights = [[1, 2, 3, 5, 6]]
long[] capacities = [10]

solver.init(values, weights, capacities)

long computedValue = solver.solve()
println "Total value = " + computedValue

var packedItems = []
var packedWeights = []
int totalWeight = 0
values.indices.each { i ->
    if (solver.bestSolutionContains(i)) {
        packedItems << i
        packedWeights << weights[0][i]
        totalWeight += weights[0][i]
    }
}
println "Total weight: $totalWeight"
println "Packed items: $packedItems"
println "Packed weights: $packedWeights"
