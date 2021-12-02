import java.io.File

fun main(){

    val example = "forward 5\n" +
            "down 5\n" +
            "forward 8\n" +
            "up 3\n" +
            "down 8\n" +
            "forward 2"

    val day = 2
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