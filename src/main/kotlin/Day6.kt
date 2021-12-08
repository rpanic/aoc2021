class Day6 : Solution {

    override fun solve1(input: List<String>): Any {

        var fish = input.first().split(",").map { it.toInt() }
        for(daysPassed in 0 until 80){

            fish = fish.map {
                if(it == 0){
                    listOf(6, 8)
                }else{
                    listOf(it - 1)
                }
            }.flatten()

        }
        return fish.size

    }

    override fun solve2(input: List<String>): Any {

        var fishList = input.first().split(",").map { it.toInt() }
        var fish = fishList.groupingBy { it }.eachCount().map { it.key to it.value.toLong() }.toMap().toMutableMap()
        (0 .. 8).forEach {
            if(!fish.containsKey(it)){
                fish[it] = 0
            }
        }

        for(daysPassed in 0 until 256){
            val zero = fish[0]!!
            fish[0] = 0
            (7 downTo 0).fold(fish[8]) { v, l ->
                val next = fish[l]
                fish[l] = v!!
                next
            }
            fish[6] = fish[6]!! + zero
            fish[8] = zero
        }
        return fish.values.sum()
    }
}