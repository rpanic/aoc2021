class Day2 : Solution {

    override fun solve1(input: List<String>): Any {
        var (hpos, depth) = 0 to 0
        input.forEach {
            val (command, value) = it.split(" ").run { this[0] to this[1].toInt() }
            when(command){
                "forward" -> hpos += value
                "down" -> depth += value
                "up" -> depth -= value
            }
        }
        return hpos * depth
    }

    override fun solve2(input: List<String>): Any {
        var (hpos, depth, aim) = listOf(0, 0, 0)
        input.forEach {
            val (command, value) = it.split(" ").run { this[0] to this[1].toInt() }
            when(command){
                "forward" -> {
                    hpos += value
                    depth += aim * value
                }
                "down" -> aim += value
                "up" -> aim -= value
            }
        }
        return hpos * depth
    }
}