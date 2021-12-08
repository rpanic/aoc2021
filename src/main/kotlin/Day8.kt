class Day8 : Solution {

    override fun solve1(input: List<String>): Any {
        val words = input.map { it.split("|").last().trim() }.flatMap { it.split(" ") }

        return words.count { it.length in listOf(2, 3, 4, 7) }

    }

    override fun solve2(input: List<String>): Any {

        val ioPairs = input.map { x -> x.split("|").map { y -> y.split(" ").map { it.trim() }.filterNot { it.isEmpty() } } }
            .map { it[0] to it[1] }

        return ioPairs.map { determineConfigAndSolve(it.first, it.second) }.map { println(it); it }.sum()

    }

    fun determineConfigAndSolve(mappings: List<String>, output: List<String>) : Int {

        val cables = 0..6
        val segments = 'a'..'g'

        fun getMapping(): Map<Char, Int> {
            permute@ while (true) {
                val perm = segments.zip(cables.shuffled()).toMap()
                for (word in mappings) {
                    val mapped = word.map { perm[it]!! }.toSet()
                    val isValidDigit = segmentsToDigits.containsKey(mapped)
                    if (!isValidDigit) continue@permute
                }
                return perm
            }
        }

        val mapping = getMapping()
        return output.map {
            segmentsToDigits[it.map { x -> mapping[x]!! }.toSet()]!!
        }.joinToString("").toInt()

    }

    //   0000
    //  1    2
    //  1    2
    //   3333
    //  4    5
    //  4    5
    //   6666

    val segmentsToDigits = mapOf(
        setOf(0, 1, 2, 4, 5, 6) to 0,
        setOf(2, 5) to 1,
        setOf(0, 2, 3, 4, 6) to 2,
        setOf(0, 2, 3, 5, 6) to 3,
        setOf(1, 2, 3, 5) to 4,
        setOf(0, 1, 3, 5, 6) to 5,
        setOf(0, 1, 3, 4, 5, 6) to 6,
        setOf(0, 2, 5) to 7,
        setOf(0, 1, 2, 3, 4, 5, 6) to 8,
        setOf(0, 1, 2, 3, 5, 6) to 9
    )
}