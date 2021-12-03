import java.io.File

fun main(){

    val example = "00100\n" +
            "11110\n" +
            "10110\n" +
            "10111\n" +
            "10101\n" +
            "01111\n" +
            "00111\n" +
            "11100\n" +
            "10000\n" +
            "11001\n" +
            "00010\n" +
            "01010"

    val day = 3
    val puzzle = 2
    val prod = true

    val solver = Class.forName("Day$day").kotlin.constructors.first().call() as Solution

    val input = if(prod) getInput(day)  else example.split("\n")

    val solution =
    if(puzzle == 1){
        solver.solve1(input)
    }else{
        solver.solve2(input)
    }

    println(solution.toString())

}

interface Solution{
    fun solve1(input: List<String>) : Any
    fun solve2(input: List<String>) : Any
}

fun getInput(day: Int) : List<String>{
    return File(Solution::class.java.getResource("inputs/day$day.txt")!!.toURI()).readLines()
}