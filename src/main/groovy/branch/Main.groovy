package branch

import groovy.transform.Canonical

record Item(int weight, int value) {}

@Canonical
class Node {
    int level, value, weight
    public int bound

    static Node next(Node parent) {
        new Node(parent.level + 1, parent.value, parent.weight)
    }
}

int bound(Node u, int n, int W, List<Item> items) {
    if (u.weight >= W)
        return 0

    int valueBound = u.value
    int j = u.level + 1
    int totalWeight = u.weight

    while (j < n && totalWeight + items[j].weight <= W) {
        totalWeight += items[j].weight
        valueBound += items[j].value
        j++
    }

    if (j < n)
        valueBound += (int) ((W - totalWeight) * items[j].value / items[j].weight)

    valueBound
}

int knapsack(int W, List<Item> items, int n) {
    items.sort { it.value / it.weight }
    var q = new PriorityQueue<>((a, b) -> b.bound <=> a.bound)
    Node u, v

    q.offer(new Node(-1, 0, 0))

    int bestValue = 0

    while (q) {
        u = q.poll()

        if (u.level == n - 1)
            continue
        else
            v = Node.next(u)

        v.weight += items[v.level].weight
        v.value += items[v.level].value

        if (v.weight <= W && v.value > bestValue)
            bestValue = v.value

        v.bound = bound(v, n, W, items)

        if (v.bound > bestValue)
            q.offer(v)

        v = Node.next(u)
        v.bound = bound(v, n, W, items)

        if (v.bound > bestValue)
            q.offer(v)
    }

    bestValue
}

int W = 10

int[] values = [1, 5, 10, 15, 17]  // Gem values
int[] weights = [1, 2, 3, 5, 6]    // Weights
var items = values.indices.collect {
    new Item(weights[it], values[it])
}

println "Total value for capacity $W = ${knapsack(W, items, values.length)}"
