class Graph(
    private val graph: Map<Node, Map<Node, Edge>>
) {
    fun distance(from: Node, to: Node): Int =
        graph.getValue(from).getValue(to).distance

    fun pheromone(from: Node, to: Node): Float =
        graph.getValue(from).getValue(to).pheromone

    fun totalPheromone(node: Node): Float =
        graph.getValue(node).map { it.value.pheromone }.sum()

    fun nextNodes(node: Node) =
        graph.getValue(node).toList()

    fun addPheromone(from: Node, to: Node, pheromoneToAdd: Float) {
        graph.getValue(from).getValue(to).pheromone += pheromoneToAdd
    }

    fun nextWithMaxPheromone(node: Node): Node =
        nextNodes(node).maxByOrNull { it.second.pheromone }!!.first
}

data class Node(val name: String)

data class Edge(val distance: Int, var pheromone: Float)