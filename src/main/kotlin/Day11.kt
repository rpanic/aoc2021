import java.util.*

typealias Octupus = Pair<Int, Int>

class Day11 : Solution {

    override fun solve1(input: List<String>): Any {

        return solution(input).first

    }

    fun solution(input: List<String>) : Pair<Any, Any> {
        var octupuses = input.map { it.toCharArray().map { x -> x.digitToInt() }.toMutableList() }
        val numOctupuses = octupuses.sumOf { it.size }

        val updateQueue: Queue<Octupus> = LinkedList()

        fun flash(oct: Octupus, flashedThisTurn: MutableSet<Octupus>){
            getAdjadcent(oct, octupuses.size to octupuses[0].size).forEach {
                octupuses[it.first][it.second]++
                updateQueue += it
            }
            flashedThisTurn += oct
        }

        var sum = 0
        var firstSyncFlash = -1

        (0 until 10000).forEach { i ->

            val flashedThisTurn = mutableSetOf<Octupus>()

            octupuses.indices.forEach { x ->
                octupuses[0].indices.forEach { y ->
                    octupuses[x][y]++
                    if(octupuses[x][y] > 9){
                        updateQueue += x to y
                    }
                }
            }

            while(updateQueue.isNotEmpty()){
                val oct = updateQueue.poll()
                if(octupuses[oct.first][oct.second] > 9 && oct !in flashedThisTurn){
                    flash(oct, flashedThisTurn)
                }
            }

            flashedThisTurn.forEach {
                octupuses[it.first][it.second] = 0
            }

            if(i < 100){
                sum += flashedThisTurn.size
            }

            if(flashedThisTurn.size == numOctupuses && firstSyncFlash == -1){
                firstSyncFlash = i
                if(i >= 100){
                    return@forEach
                }
            }

        }

        return sum to firstSyncFlash + 1
    }

    fun getAdjadcent(point: Octupus, sizes: Pair<Int, Int>) : List<Octupus>{
        val (x, y) = point
        return listOf(
            x-1 to y-1, x to y-1, x+1 to y-1,
            x-1 to y, x to y, x+1 to y,
            x-1 to y+1, x to y+1, x+1 to y+1,
        ).filter { it.first >= 0 && it.first < sizes.first && it.second >= 0 && it.second < sizes.second }
    }

    override fun solve2(input: List<String>): Any {
        return solution(input).second
    }
}