import kotlin.random.Random

class AntSystem(
    private val graph: Graph
) {
    fun run(from: Node, to: Node): List<Node> {
        for (n in 0..100) {
            val tour = Ant(graph).run(from, to)
            updatePheromone(tour)
        }

        return bestPath(from, to)
    }

    private fun updatePheromone(tour: Tour) {
        val pheromoneToAdd = 1.0F / tour.distance
        for (i in 0 until tour.path.size - 1) {
            graph.addPheromone(tour.path[i], tour.path[i + 1], pheromoneToAdd)
        }
    }

    private fun bestPath(from: Node, to: Node): List<Node> {
        val bestPath = mutableListOf(from)
        var actual = from
        var next: Node? = null
        while (next != to) {
            next = graph.nextWithMaxPheromone(actual)
            bestPath.add(next)
            actual = next
        }
        return bestPath
    }
}

class Ant(
    private val graph: Graph
) {
    fun run(from: Node, to: Node): Tour {
        val path = mutableListOf(from)
        var pathDistance = 0
        var actual = from
        var next: Node? = null
        while (next != to) {
            next = chooseNext(actual)
            path.add(next)
            pathDistance += graph.distance(actual, next)
            actual = next
        }

        return Tour(path, pathDistance)
    }

    private fun chooseNext(actual: Node): Node {
        val totalPheromone = graph.totalPheromone(actual)
        val u = Random.nextFloat()
        var accumulativeProb = 0.0
        for((next, edge) in graph.nextNodes(actual)) {
            val nextProbability = edge.pheromone / totalPheromone
            if (u in accumulativeProb..accumulativeProb+nextProbability)
                return next

            accumulativeProb += nextProbability
        }
        return actual
    }
}

data class Tour(val path: List<Node>, val distance: Int)
