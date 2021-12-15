import utils.Neighbors4
import utils.Vector2D
import java.util.*

class Day15 : Solution {

    fun getNeighbors(point: Coords, grid: List<List<Int>>): List<Coords>{

        return Neighbors4.map { point.plus(it.step) }
            .filter { it.x >= 0 && it.y >= 0 && it.x < grid.size && it.y < grid[it.x].size }

    }

    fun djikstra(levels: List<List<Int>>) : List<Coords>{
        val distances = mutableMapOf<Coords, Int>()
        val predecessor = mutableMapOf<Coords, Coords>()

        val q = PriorityQueue<Pair<Coords, Int>> { a, b ->
            a.second.compareTo(b.second)
        }
        val done = mutableSetOf<Coords>()

        distances[0 vec 0] = 0
        q.offer(0 vec 0 to 0)

        println("Init complete")

        while(q.isNotEmpty()){

            val (n, distN) = q.poll()
            require(distN == distances[n])
//            val distN = distances[n]!!
            getNeighbors(n, levels).filter { it !in done }.forEach { c ->
                //Update distance
                val cDistance = distN + levels[c.x][c.y]
                if(cDistance < distances.getOrDefault(c, Int.MAX_VALUE)){
                    distances[c] = cDistance
                    predecessor[c] = n
                    q.removeIf { it.first == c }
                    q.offer(c to cDistance)
                }
            }
            done += n

        }

        val end = levels.size - 1 vec levels[levels.size-1].size - 1
        val path = mutableListOf(end)
        while(predecessor[path.last()] != null){
            path += predecessor[path.last()]!!
        }
        return path
    }

    override fun solve1(input: List<String>): Any {

        val levels = input.map { it.toCharArray().map { a -> a.digitToInt() } }
        val path = djikstra(levels)
        return path.reversed().drop(1).sumOf { levels[it.x][it.y] }

    }

    override fun solve2(input: List<String>): Any {

        val levels = input.map { it.toCharArray().map { a -> a.digitToInt() } }

        val levels1 = levels.map { orig ->
            (0..4).flatMap { i -> orig.map { el -> ((el + i) % 10).run { if(this < el) this + 1 else this } } }
        }
        val levels2 = (0..4).flatMap { i ->
            levels1.indices.map { i1 ->
                levels1[i1].map { el -> ((el + i) % 10).run { if(this < el) this + 1 else this } }
        }}

        val path = djikstra(levels2)
        return path.reversed().drop(1).sumOf { levels2[it.x][it.y] }

    }

    infix fun Int.vec(y: Int) : Vector2D{
        return Vector2D(this, y)
    }
}

typealias Coords = Vector2D