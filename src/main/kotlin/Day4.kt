class Day4 : Solution {

    data class Board(val values: List<List<Int>>, val marked: List<Int>){

        fun mark(number: Int) = if(checkSolution()) this else Board(values, marked + number)

        fun checkSolution() : Boolean{
            return values.any { it.all { x -> x in marked } } ||
                    values.indices.any { x -> values.indices.all { values[it][x] in marked } }
        }

        fun score() = (values.flatten() - marked.toSet()).sum() * marked.last()

    }

    fun findScores(input: List<String>) : List<Board> {
        val numbers = input[0].split(",").map { it.toInt() }

        val boards = input.drop(2).windowed(5, 6).map{ i ->
            val board = i.map { line -> line.split(" ").filter { it.trim().isNotEmpty() }.map { it.toInt() }.toMutableList() }
            board
        }.map{ Board(it, emptyList()) }

        return numbers.fold(boards) { boards2, number ->
            boards2.map { b -> b.mark(number) }
        }
    }

    override fun solve1(input: List<String>): Any {

        return findScores(input).minByOrNull { it.marked.size }!!.score()

    }

    override fun solve2(input: List<String>): Any {

        return findScores(input).maxByOrNull { it.marked.size }!!.score()

    }

}