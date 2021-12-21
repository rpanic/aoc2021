class Day21 : Solution {

    class DeterministicDice(val faces: Int){

        var last = 0

        var diced = 0

        fun roll() : Int{
            var current = last + 1
            if(current > faces){
                current = 1
            }
            last = current
            diced++
            return current
        }

    }

    class Player(var score: Int = 0){
        fun addScore(add: Int){
            score += add
        }
    }

    override fun solve1(input: List<String>): Any {

        var (p1, p2) = input.map { it.takeLast(2).trim().toInt() }
        var (pointsp1, pointsp2) = 0 to 0

        p1--
        p2--

        val dice = DeterministicDice(100)

        fun diceThreeTimes() : Int{
            return (0..2).map { dice.roll() }.sum()
        }

        while(pointsp1 < 1000 && pointsp2 < 1000){

            p1 = (p1 + diceThreeTimes()) % 10
            pointsp1 += p1 + 1

            if(pointsp1 >= 1000){
                continue
            }

            p2 = (p2 + diceThreeTimes()) % 10
            pointsp2 += p2 + 1

        }

        val (min, max) = listOf(pointsp1, pointsp2).sorted()

        return min * dice.diced

    }

    override fun solve2(input: List<String>): Any {

//        val allPossibilities = (0..20).map { mutableListOf<Int>() }.toList()


        return 0
    }
}