import java.util.*

class Day12 : Solution {

    data class Node(
        val name: String,
        val neighbors: MutableList<Node> = mutableListOf()
    ){
        fun isBig() = name[0].isUpperCase()

        fun isSmall() = name[0].isLowerCase() && name != "start"

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Node

            if (name != other.name) return false

            return true
        }

        override fun hashCode(): Int {
            return name.hashCode()
        }

        override fun toString(): String {
            return "Node(name='$name')"
        }


    }

    fun parseNodes(input: List<String>) : Set<Node>{
        val nodes = mutableSetOf<Node>()

        input.forEach { line ->
            val (start, end) = line.split("-")
            val startNode = nodes.find { it.name == start } ?: Node(start)
            val endNode = nodes.find { it.name == end } ?: Node(end)
            startNode.neighbors += endNode
            endNode.neighbors += startNode
            nodes += startNode
            nodes += endNode
        }

        return nodes
    }

    override fun solve1(input: List<String>): Any {

        val nodes = parseNodes(input)

        val foundPaths = mutableSetOf<List<Node>>()

        val stack = Stack<Pair<Node, List<Node>>>()
        stack.push(nodes.find { it.name == "start" }!! to listOf())

        while(stack.isNotEmpty()){
            val (p, pathUntil) = stack.pop()

            if(p.name == "end"){
                foundPaths += pathUntil + p
                continue
            }

            val newPath = pathUntil + p

            p.neighbors.filter { it.isBig() || it !in pathUntil }
                .forEach { child ->
                    stack.push(child to newPath)
                }

        }

        return foundPaths.size

    }

    override fun solve2(input: List<String>): Any {

        val nodes = parseNodes(input)

        val foundPaths = mutableSetOf<List<Node>>()

        val stack = Stack<Pair<Node, List<Node>>>()
        stack.push(nodes.find { it.name == "start" }!! to listOf())

        while(stack.isNotEmpty()){
            val (p, pathUntil) = stack.pop()

            if(p.name == "end"){
                foundPaths += pathUntil + p
                continue
            }

            val newPath = pathUntil + p

            val hasSecondSmallCave = newPath.filter { it.isSmall() }.groupingBy { it.name }.eachCount().any { it.value > 1 }

            p.neighbors.filter { if(hasSecondSmallCave) it.isBig() || it !in pathUntil else it.name != "start" }
                .forEach { child ->
                    stack.push(child to newPath)
                }

        }

        return foundPaths.size

    }
}