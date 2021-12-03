import java.lang.Math.pow
import kotlin.math.pow

class Day3 : Solution {

    override fun solve1(input: List<String>): Any {

        val parsedInput = input.map { Integer.parseInt(it, 2) }

        val gamma = (0 until input.first().length).sumOf { index ->

            val value = 2.0.pow(index.toDouble()).toInt()
            val count = parsedInput.groupingBy { it.and(value) == value }.eachCount()
            if ((count[true] ?: 0) > (count[false] ?: 0)) value else 0

        }
        val epsilon = (2.0.pow(input.first().length) - 1).toInt() - gamma
        return "${gamma * epsilon}"

    }

    override fun solve2(input: List<String>): Any {

        val parsedInput = input.map { Integer.parseInt(it, 2) }
        val length = input.first().length

        val findCommonNumber = { index: Int, list: List<Int> ->
            val value = 2.0.pow((length - index - 1).toDouble()).toInt()
            val count = list.groupingBy { it.and(value) == value }.eachCount()
            if((count[false] ?: 0) > (count[true] ?: 0)) value to false else value to true
        }

        val findLeastCommonNumber = { index: Int, list: List<Int> ->
            val value = 2.0.pow((length - index - 1).toDouble()).toInt()
            val count = list.groupingBy { it.and(value) == value }.eachCount()
            if((count[true] ?: 0) >= (count[false] ?: 0)) value to false else value to true
        }

        val getRating = { criteria: (index: Int, list: List<Int>) -> Pair<Int, Boolean> ->
            var list = parsedInput;
            var index = 0;
            while(list.size > 1){
                val (value, shouldInclude) = criteria(index, list)

                list = if(shouldInclude){
                    list.filter { it.and(value) == value }
                }else{
                    list.filter { it.and(value) != value }
                }

                index++
            }
            list.first()
        }

        val oxygen = getRating(findCommonNumber)
        val co2 = getRating(findLeastCommonNumber)

        return "${ oxygen * co2 }"

    }

}