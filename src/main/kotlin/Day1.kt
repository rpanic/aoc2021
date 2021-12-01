class Day1 : Solution {

    override fun solve1(input: List<String>): String {
        return input.map{ it.toInt() }.zipWithNext().count { (a, b) -> b > a }.toString()
    }

    override fun solve2(input: List<String>): String {
        return input.map{ it.toInt() }.windowed(3).zipWithNext().count { (a, b) -> b.sum() > a.sum() }.toString()
    }
}