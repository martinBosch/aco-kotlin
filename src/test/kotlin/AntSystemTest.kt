import kotlin.test.Test
import kotlin.test.assertEquals

internal class AntSystemTest {

    @Test
    fun `foo bar`() {
        val graph = mapOf(
            Node("A") to mapOf(
                Node("B") to Edge(distance = 1, pheromone = 1.0F),
                Node("C") to Edge(distance = 1, pheromone = 1.0F)),
            Node("B") to mapOf(
                Node("C") to Edge(distance = 1, pheromone = 1.0F)),
            Node("C") to mapOf(
                Node("D") to Edge(distance = 1, pheromone = 1.0F),
                Node("E") to Edge(distance = 1, pheromone = 1.0F)),
            Node("D") to mapOf(
                Node("E") to Edge(distance = 1, pheromone = 1.0F)),
        )
        val aco = AntSystem(Graph(graph))

        val result = aco.run(from = Node("A"), to = Node("E"))

        assertEquals(listOf(Node("A"), Node("C"), Node("E")), result)
    }
}