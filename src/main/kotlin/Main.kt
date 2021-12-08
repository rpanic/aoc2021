import java.io.File

fun main(){

    val example = "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb |" +
            "fdgacbe cefdb cefbgd gcbe\n" +
            "edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec |" +
            "fcgedb cgb dgebacf gc\n" +
            "fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef |" +
            "cg cg fdcagb cbg\n" +
            "fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega |" +
            "efabcd cedba gadfec cb\n" +
            "aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga |" +
            "gecf egdcabf bgf bfgea\n" +
            "fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf |" +
            "gebdcfa ecba ca fadegcb\n" +
            "dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf |" +
            "cefg dcbef fcge gbcadfe\n" +
            "bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd |" +
            "ed bcgafe cdgba cbgef\n" +
            "egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg |" +
            "gbdfcae bgc cg cgb\n" +
            "gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc |" +
            "fgae cfgab fg bagce"

    val day = 8
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