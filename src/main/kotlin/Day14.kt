typealias InsertionRule = Triple<Char, Char, Char>

class Day14 : Solution {

    fun InsertionRule.signature() = "${this.first}${this.second}"

    fun parseInput(input: List<String>) : Pair<String, List<InsertionRule>>{

        val start = input.first()

        val rules = input.drop(2).map {
            val (pair, insert) = it.split(" -> ")
            InsertionRule(pair[0], pair[1], insert[0])
        }

        return start to rules
    }

    override fun solve1(input: List<String>): Any {

        var (polymer, rules) = parseInput(input)

        (0 until 10).forEach { i ->

            polymer = polymer.zipWithNext().joinToString("") {
                val insert = rules.find { r -> r.first == it.first && r.second == it.second }!!.third
                it.first.toString() + insert
            } + polymer.last()

            println(polymer)

        }

        val counts = polymer.groupingBy { it }.eachCount()
        val max = counts.maxByOrNull { it.value }!!
        val min = counts.minByOrNull { it.value }!!

        return max.value - min.value

    }

    override fun solve2(input: List<String>): Any {

        var (polymer, rules) = parseInput(input)

        var rulesList = rules.associate { it.signature() to 0L }.toMutableMap()
        polymer.zipWithNext().forEach {
            rulesList["${it.first}${it.second}"] = rulesList["${it.first}${it.second}"]!! + 1
        }

        (0 until 40).forEach { i ->

            rulesList = rulesList.map {
                val rule = rules.find { r -> r.signature() == it.key }!!
                listOf("${rule.first}${rule.third}" to it.value, "${rule.third}${rule.second}" to it.value)
            }.flatten().groupBy { it.first }.map { it.key to it.value.sumOf { x -> x.second } }
                .toMap().toMutableMap()

        }

        val counts = rulesList.map { it.key[1] to it.value }.groupBy { it.first }.map { it.key to it.value.sumOf { v -> v.second } }.toMap().toMutableMap()

        counts[polymer[0]] = counts[polymer[0]]!! + 1

        val max = counts.maxByOrNull { it.value }!!
        val min = counts.minByOrNull { it.value }!!

        return max.value - min.value

    }
}