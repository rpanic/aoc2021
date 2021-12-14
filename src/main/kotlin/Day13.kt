import utils.Vector2D
import java.lang.StringBuilder
import java.util.*

class Day13 : Solution {

    fun parsePaper(input: List<String>) : Pair<List<Vector2D>, List<Pair<String, String>>>{

        val points = input.takeWhile { it != "" }.map {
            val (x, y) = it.split(",")
            Vector2D(x.toInt(), y.toInt())
//            x.toInt() to y.toInt()
        }

//        val maxX = points.maxOf { it.first }
//        val maxY = points.maxOf { it.second }
//
//        val paper = initFalseArray(maxX, maxY)
//        points.forEach {
//            paper[it.first][it.second] = true
//        }

        val folds = input.takeLastWhile { it != "" }.map { it.removePrefix("fold along ").split("=") }.map { val (a, b) = it; a to b }

        return points to folds

    }

    fun initFalseArray(x: Int, y: Int) : MutableList<MutableList<Boolean>>{
        return mutableListOf(
            *(0 .. x).map {
                mutableListOf(*(0..y).map { false }.toTypedArray())
            }.toTypedArray()
        )
    }

    fun doFolds(input: List<String>, numFolds: Int = -1) : List<Vector2D>{
        var (points, folds) = parsePaper(input)

        (if(numFolds != -1) folds.take(numFolds) else folds)
        .forEach { fold ->

            val foldPoint = fold.second.toInt()
            if(fold.first == "x"){

                points.filter { it.x > foldPoint }.forEach {
                    it.x = foldPoint - (it.x - foldPoint)
                }

            }else if(fold.first == "y") {

                points.filter { it.y > foldPoint }.forEach {
                    it.y = foldPoint - (it.y - foldPoint)
                }

            }
        }

        return points.distinctBy { "${it.x} ${it.y}" }

    }

    override fun solve1(input: List<String>): Any {

        val points = doFolds(input, 1)
        return points.size

    }

    override fun solve2(input: List<String>): Any {

        val points = doFolds(input)
        //Print it
        val maxX = points.maxOf { it.x }
        return points.groupBy { it.y }
            .map { (y, points) ->
                var s = StringBuilder(maxX + 1)
                s.setRange(0, maxX + 1, ".".repeat(maxX + 1))

                points.forEach {
                    s[it.x] = '#'
                }
                s.toString() to y

            }.sortedBy { it.second }
            .map { it.first }
            .joinToString("\n")

    }
}