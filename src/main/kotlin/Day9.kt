import java.util.*

class Day9 : Solution {

    override fun solve1(input: List<String>): Any {
        val heightmap = input.map { it.toCharArray().map { x -> x.digitToInt() } }

        val lowpoints = (heightmap.indices).flatMap { i ->
            (heightmap[i].indices).mapNotNull { j ->
                val value= heightmap[i][j]
                val lowpoint = listOf(i - 1 to j, i + 1 to j, i to j-1, i to j+1)
                    .filter { it.first >= 0 && it.second >= 0 && it.first < heightmap.size && it.second < heightmap[i].size }
                    .all { heightmap[it.first][it.second] > value }
                if(lowpoint) value else null
            }
        }
        return lowpoints.sum() + lowpoints.size

    }

    override fun solve2(input: List<String>): Any {
        val heightmap = input.map { it.toCharArray().map { x -> x.digitToInt() } }

        fun getAscending(point: Pair<Int, Int>): List<Pair<Int, Int>> {
            val (i, j) = point
            return listOf(i - 1 to j, i + 1 to j, i to j-1, i to j+1)
                .filter { it.first >= 0 && it.second >= 0 && it.first < heightmap.size && it.second < heightmap[i].size }
        }

        val lowpoints = (heightmap.indices).flatMap { i ->
            (heightmap[i].indices).mapNotNull { j ->
                val value = heightmap[i][j]
                val lowpoint = getAscending(i to j)
                    .all { heightmap[it.first][it.second] > value }
                if(lowpoint) i to j else null
            }
        }

        val basinMap = heightmap.indices.map { mutableListOf(*(0 until heightmap[0].size).map { 0 }.toTypedArray()) }

        lowpoints.forEachIndexed { index, start ->

            val basinNum = index + 1

            if(basinMap[start.first][start.second] != 0){
                return@forEachIndexed
            }

            val queue: Queue<Pair<Int, Int>> = LinkedList()
            queue.add(start)
            basinMap[start.first][start.second] = basinNum

            while(queue.isNotEmpty()){

                val point = queue.poll()
                getAscending(point).filter { (i,j) -> heightmap[i][j] < 9 }
                    .filter { (i,j) -> basinMap[i][j] == 0 }
                    .forEach {
                        queue.add(it)
                        basinMap[it.first][it.second] = basinNum
                    }

            }
        }

        return basinMap.flatten().groupingBy { it }.eachCount()
            .filter { it.key > 0 }
            .toList()
            .sortedByDescending { it.second }
            .take(3)
            .map { it.second }
            .reduce { acc, x -> acc * x }

    }
}