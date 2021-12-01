import java.io.File

fun main(){

    val example = "199\n" +
            "200\n" +
            "208\n" +
            "210\n" +
            "200\n" +
            "207\n" +
            "240\n" +
            "269\n" +
            "260\n" +
            "263"

    val day = 1
    val puzzle = 2
    val prod = true

    val solver = Day1()

    val input = if(prod) getInput(day)  else example.split("\n")

    val solution =
    if(puzzle == 1){
        solver.solve1(input)
    }else{
        solver.solve2(input)
    }

    println(solution)

}

interface Solution{
    fun solve1(input: List<String>) : String
    fun solve2(input: List<String>) : String
}

fun getInput(day: Int) : List<String>{
    return File(Solution::class.java.getResource("inputs/day$day.txt")!!.toURI()).readLines()
}