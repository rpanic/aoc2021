import utils.Vector2D
import kotlin.math.sign

class Day5 : Solution {

    data class Line2D(val start: Vector2D, val end: Vector2D){

        companion object{
            fun parseFromLine(line: String) : Line2D{
                val points = line.split("->").map { it.trim().split(",").map { x -> x.toInt() } }
                    .map { Vector2D(it[0], it[1]) }

                return Line2D(points[0], points[1])
            }
        }

    }

    override fun solve1(input: List<String>): Any {

        val lines = input.map { Line2D.parseFromLine(it) }
            .filter { it.start.x == it.end.x || it.start.y == it.end.y }

        val points = lines
            .map { (start, end) ->
                val dir = Vector2D((end.x - start.x).sign, (end.y - start.y).sign)
                val list = mutableListOf<Vector2D>()
                var curr = start
                while(curr != end){
                    list.add(curr)
                    curr = curr + dir
                }
                list.add(curr)
                list.toList()
            }.flatten().groupBy { "${it.x} ${it.y}" }.map { it.key to it.value.size }

        return points.count { it.second > 1 }

    }

    override fun solve2(input: List<String>): Any {

        val lines = input.map { Line2D.parseFromLine(it) }

        val points = lines
            .map { (start, end) ->
                val dir = Vector2D((end.x - start.x).sign, (end.y - start.y).sign)
                val list = mutableListOf<Vector2D>()
                var curr = start
                while(curr != end){
                    list.add(curr)
                    curr = curr + dir
                }
                list.add(curr)
                list.toList()
            }.flatten().groupBy { "${it.x} ${it.y}" }.map { it.key to it.value.size }

        return points.count { it.second > 1 }

    }
}