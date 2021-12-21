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

        val (min, _) = listOf(pointsp1, pointsp2).sorted()

        return min * dice.diced

    }

    data class GameState(val p1: Int, val p2: Int, val score1: Int, val score2: Int){
    }

    override fun solve2(input: List<String>): Any {

        val cache = mutableMapOf<GameState, Pair<Long, Long>>()

        fun countWins(g: GameState) : Pair<Long, Long>{
            return if(g.score1 >= 21){
                1L to 0L
            }else if(g.score2 >= 21){
                0L to 1L
            }else if(g in cache){
                cache[g]!!
            }else{

                var ans = 0L to 0L

                (1 .. 3).cartesian(1 .. 3).cartesian(1 .. 3).forEach { (p, c) ->

                    val (a, b) = p
                    val newp1 = (g.p1 + a + b + c) % 10
                    val newscore1 = g.score1 + newp1 + 1

                    val rec = countWins(GameState(g.p2, newp1, g.score2, newscore1))
                    ans = ans.first + rec.second to ans.second + rec.first

                }

                cache[g] = ans
                ans
            }

        }

        var (p1, p2) = input.map { it.takeLast(2).trim().toInt() }

        val ret = countWins(GameState(p1-1, p2-1, 0, 0))

        return listOf(ret.first, ret.second).maxOrNull()!!
    }
}