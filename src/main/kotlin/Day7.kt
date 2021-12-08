import kotlin.math.abs

class Day7 : Solution {

    override fun solve1(input: List<String>): Any {
        val positions = input.first().split(",").map { it.toInt() }

        val maxPos = positions.maxOrNull()!!

        var minFuel = Int.MAX_VALUE
        var minFuelPos = -1
        var minMissedCount = 0

        var lower = maxPos / 2
        var upper = maxPos / 2 + 1
        var lowerUpper = true
        while(lower >= 0 && upper <= maxPos && minMissedCount < 5){
            val index = if(lowerUpper) lower else upper

            val totalFuel = positions.map { abs(index - it) }.sum()
            if(totalFuel < minFuel){
                minFuel = totalFuel
                minFuelPos = index
                minMissedCount = 0
            }else{
                minMissedCount++
            }

            if(lowerUpper){
                lower--
            }else{
                upper++
            }
            lowerUpper = !lowerUpper
        }

        return minFuel

    }

    override fun solve2(input: List<String>): Any {

        val positions = input.first().split(",").map { it.toInt() }

        val maxPos = positions.maxOrNull()!!

        var minFuel = Int.MAX_VALUE
        var minFuelPos = -1
        var minMissedCount = 0

        var lower = maxPos / 2
        var upper = maxPos / 2 + 1
        var lowerUpper = true
        while(lower >= 0 && upper <= maxPos && minMissedCount < 5){
            val index = if(lowerUpper) lower else upper

            val totalFuel = positions.map {
                val n = abs(index - it)
                (n * (n + 1)) / 2
            }.sum()

            if(totalFuel < minFuel){
                minFuel = totalFuel
                minFuelPos = index
                minMissedCount = 0
            }else{
                minMissedCount++
            }

            if(lowerUpper){
                lower--
            }else{
                upper++
            }
            lowerUpper = !lowerUpper
        }

        return minFuel

    }
}