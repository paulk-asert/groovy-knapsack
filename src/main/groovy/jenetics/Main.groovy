package jenetics

import io.jenetics.BitGene
import io.jenetics.MultiPointCrossover
import io.jenetics.Mutator
import io.jenetics.RouletteWheelSelector
import io.jenetics.SinglePointCrossover
import io.jenetics.TournamentSelector
import io.jenetics.engine.Codec
import io.jenetics.engine.Codecs
import io.jenetics.engine.Engine
import io.jenetics.engine.EvolutionResult
import io.jenetics.engine.Problem
import io.jenetics.util.ISeq

import java.util.function.Function

import static io.jenetics.engine.EvolutionResult.toBestPhenotype
import static io.jenetics.engine.Limits.bySteadyFitness

record Item(int weight, int value) implements Serializable {}

class Knapsack implements Problem<ISeq<Item>, BitGene, Integer> {
    private final Codec<ISeq<Item>, BitGene> codec
    private final double knapsackSize

    Knapsack(ISeq<Item> items, int knapsackSize) {
        codec = Codecs.ofSubSet(items)
        this.knapsackSize = knapsackSize
    }

    @Override
    Function<ISeq<Item>, Integer> fitness() {
        (items) -> {
            var sum = items.inject(new Item(0, 0)) { acc, next ->
                new Item(acc.weight + next.weight, acc.value + next.value)
            }
            sum.weight <= knapsackSize ? sum.value : 0
        }
    }

    @Override
    Codec<ISeq<Item>, BitGene> codec() { codec }
}

int W = 10
int[] values = [1, 5, 10, 15, 17]  // Gem values
int[] weights = [1, 2, 3, 5, 6]    // Weights
var items = [weights, values].transpose().collect { w, v -> new Item(w, v) }
var iSeq = items.stream().collect(ISeq.toISeq())
var knapsack = new Knapsack(iSeq, W)

var engine = Engine.builder(knapsack)
    .populationSize(8)
    .survivorsSelector(new TournamentSelector<>(3))
    .offspringSelector(new RouletteWheelSelector<>())
    .alterers(
        new Mutator<>(0.1),
        new SinglePointCrossover<>(0.2),
        new MultiPointCrossover<>(0.1))
    .build()

var log = { EvolutionResult er ->
    var avg = er.population().average{ it.fitness() }
    var best = er.bestFitness()
    printf "avg = %5.2f, best = %d%n", avg, best
}

var best = engine.stream()
    .limit(bySteadyFitness(8))
    .limit(30)
    .peek(log)
    .collect(toBestPhenotype())

println best

/*

avg = 18.88, best = 23
avg = 21.00, best = 25
avg = 22.00, best = 25
avg = 22.63, best = 25
avg = 25.63, best = 30
avg = 27.50, best = 30
avg = 27.63, best = 30
avg = 24.38, best = 30
avg = 22.50, best = 30
avg = 26.25, best = 30
avg = 30.00, best = 30
avg = 30.00, best = 30
[00001110] -> 30

avg = 16.75, best = 27
avg = 17.13, best = 23
avg = 18.00, best = 23
avg = 21.38, best = 27
avg = 24.00, best = 27
avg = 24.88, best = 27
avg = 22.50, best = 27
avg = 26.88, best = 27
[00010100] -> 27

*/
