import kotlin.math.sign

class Day17 : Solution {

    data class Probe(var x: Int, var y: Int, var velX: Int, var velY: Int) {
        fun step() {
            x += velX
            y += velY
            if (velX > 0) velX--
            if (velX < 0) velX++
            velY--
        }

        fun hasPassed(r: Rectangle): Boolean {
            return y < r.startY
        }
    }

    data class Rectangle(val startX: Int, val endX: Int, val startY: Int, val endY: Int) {
        fun contains(x: Int, y: Int): Boolean {
            return x in startX..endX && y in startY..endY
        }

        fun contains(p: Probe) = contains(p.x, p.y)
    }

    data class SimResult(val velX: Int, val velY: Int, val highpoint: Int)

    fun parseInput(input: String) : Pair<IntRange, IntRange>{
        val (linex, liney) = input.removePrefix("target area: ").split(", ")
        val rangex = linex.removePrefix("x=").split("..").map { it.toInt() }.let { (a, b) -> a .. b }
        val rangey = liney.removePrefix("y=").split("..").map { it.toInt() }.let { (a, b) -> a .. b }
        return rangex to rangey
    }

    fun computePower(range: IntRange, changingF: (Int) -> Int, continueF: (Int, Int, IntRange) -> Boolean) : Set<Int>{

        val (lower, upper) = range.first to range.last
        val possibles = mutableSetOf<Int>()

        power@ for(initialPower in -10000 .. 10000){

            var x = 0
            var speed = initialPower
            while(continueF(x, speed, range)){

                x += speed
                speed = changingF(speed)
                if(x in range){
                    possibles += initialPower
                    continue@power
                }

            }
        }
        return possibles

    }

    fun getOptionsWithinTarget(ranges: Pair<IntRange, IntRange>) : Set<Triple<Int, Int, Int>>{
        val (xr, yr) = ranges
        val possibleXs = computePower(xr, { it + -1 * it.sign }, { x, speed, r -> x <= r.last && speed != 0 })
        val possibleYs = computePower(yr, { it + -1 }, { y, _, r -> y >= r.first})

        val reaching = possibleXs.cartesian(possibleYs).mapNotNull {

            var (x, y) = 0 to 0
            var (speedX, speedY) = it
            var highpoint = Int.MIN_VALUE
            var reached = false
            while(!reached && x <= xr.last && y >= yr.first){

                x += speedX
                y += speedY
                speedX += -1 * speedX.sign
                speedY--

                if(y > highpoint) highpoint = y

                if(x in xr && y in yr){
                    reached = true
                }

            }
            if(reached) Triple(it.first, it.second, highpoint) else null
        }.toSet()
        return reaching

    }

    override fun solve1(input: List<String>): Any {

        val (xr, yr) = parseInput(input.first())

        val possibles = getOptionsWithinTarget(xr to yr)

        var high = possibles.maxByOrNull { it.third }!!
        return high.third

    }

    override fun solve2(input: List<String>): Any {

        val (xr, yr) = parseInput(input.first())

        val possibles = getOptionsWithinTarget(xr to yr)

        return possibles.size


    }
}

fun <T, V> Iterable<T>.cartesian(i2: Iterable<V>) : Set<Pair<T, V>>{
    val set = mutableSetOf<Pair<T, V>>()
    for(i in this){
        for(j in i2){
            set += i to j
        }
    }
    return set
}