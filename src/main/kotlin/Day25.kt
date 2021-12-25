import utils.Vector2D

class Day25 : Solution {

    fun parseArray(input: List<String>) = input.map { it.toCharArray() }

    override fun solve1(input: List<String>): Any {

        val array = parseArray(input)

        val lengthX = array.size
        val lengthY = array[0].size

        val turns = (1..10000000).first { move ->

            val movesR = array.flatMapIndexed { i, a -> a.mapIndexed { j, v ->
                if(v == '>' && array[i][(j + 1) % lengthY] == '.') i to j else null
            }.filterNotNull() }

            movesR.forEach { (i, j) ->
                array[i][j] = '.'
                array[i][(j+1) % lengthY] = '>'
            }

            val movesD = array.flatMapIndexed { i, a -> a.mapIndexed { j, v ->
                if(v == 'v' && array[(i + 1) % lengthX][j] == '.') i to j else null
            }.filterNotNull() }

            movesD.forEach { (i, j) ->
                array[i][j] = '.'
                array[(i+1) % lengthX][j] = 'v'
            }

            movesR.isEmpty() && movesD.isEmpty()

        }
        return turns

    }

    override fun solve2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}