import kotlin.math.max
import kotlin.math.min

class Day22 : Solution {

    data class CuboidInstruction(val cuboid: Cuboid, val mode: Boolean)

    data class Cuboid(var x: IntRange, var y: IntRange, var z: IntRange){

        fun limit(from: Int, to: Int) : Cuboid?{
            val newRanges = listOf(x, y, z).map {
                max(it.first, from) .. min(it.last, to)
            }
            if(newRanges.any { it.last < from || it.first > to }){
                return null
            }
            return Cuboid(newRanges[0], newRanges[1], newRanges[2])
        }

        fun hasSubCuboid(sub: Cuboid) : Boolean{
            return this.x.intersect(sub.x) == sub.x &&
                    this.y.intersect(sub.y) == sub.y &&
                    this.z.intersect(sub.z) == sub.z
        }

        fun numCubes() : Long {
            return listOf(x, y, z).map { (it.last - it.first + 1).toLong() }.reduce { a, b -> a * b }
        }

        fun distinctCuboids(c2: Cuboid) : List<Cuboid>{

            val cuboids = this.x.parts(c2.x).cartesian(y.parts(c2.y)).cartesian(z.parts(c2.z)).map { (p, c) ->
                val (a, b) = p
                Cuboid(a, b, c)
            }.filter { this.hasSubCuboid(it) || c2.hasSubCuboid(it) }
            return cuboids
        }

        fun intersect(c2: Cuboid) : Cuboid?{

            val i = Cuboid(this.x.intersect(c2.x), this.y.intersect(c2.y), this.z.intersect(c2.z))
            return if(i.x.isEmpty() || i.y.isEmpty() || i.z.isEmpty()){
                null
            }else {
                i
            }

        }

        override fun toString(): String {
            return "Cuboid($x, $y, $z)"
        }

    }

    fun parseInput(input: List<String>, limit: Pair<Int, Int>) : List<CuboidInstruction>{

        return input.mapNotNull {
            val (mode, rangesString) = it.split(" ")
            val ranges = rangesString.split(",")
            fun parseRange(s: String, prefix: String) : IntRange{
                val (a, b) = s.removePrefix(prefix).split("..").map { sub -> sub.toInt() }
                return a..b
            }
            val cuboid = Cuboid(parseRange(ranges[0], "x="), parseRange(ranges[1], "y="), parseRange(ranges[2], "z="))
            val limited = cuboid.limit(limit.first, limit.second)
            if(limited == null){
                null
            }else {
                CuboidInstruction(limited, mode == "on")
            }
        }

    }

    fun solveWithinLimits(input: List<String>, range: Pair<Int, Int>) : Long{

        val instructions = parseInput(input, range)

        val list = instructions.fold(mutableListOf<CuboidInstruction>()) { acc, inst ->
            acc.toList().forEachIndexed { index, ci ->
                val i = ci.cuboid.intersect(inst.cuboid)
                if(i != null){
                    val parts = ci.cuboid.distinctCuboids(inst.cuboid)
                    acc.remove(ci)
                    acc.addAll(parts.filter { ci.cuboid.hasSubCuboid(it) && !inst.cuboid.hasSubCuboid(it) }.map { CuboidInstruction(it, ci.mode) })
                }
            }
            acc.add(inst)
            acc
        }

        return list.filter { it.mode }.map { it.cuboid.numCubes() }.sum()

    }

    override fun solve1(input: List<String>) = solveWithinLimits(input, -50 to 50)

    override fun solve2(input: List<String>) = solveWithinLimits(input, Int.MIN_VALUE to Int.MAX_VALUE)
}

fun IntRange.sorted(range: IntRange) : Pair<IntRange, IntRange>{
    return if(range.first < this.first)
        range to this
    else
        this to range
}

fun IntRange.intersect(range: IntRange) : IntRange{

    val (a, b) = this.sorted(range)

    if(b.first > a.last){
        return IntRange.EMPTY
    }

    return if(a.last < b.last){
        b.first .. a.last
    }else{
        b
    }
}

fun IntRange.dissect(range: IntRange) : List<IntRange>{
    val (a, b) = this.sorted(range)

    if(b.first > a.last){
        return listOf(a, b)
    }

    return if(a.last < b.last){
        listOf(a.first until b.first, a.last + 1 .. b.last)
    }else{
        listOf(a.first until b.first, b.last + 1 .. a.last)
    }
}

fun IntRange.parts(range: IntRange) : List<IntRange>{
    return (this.dissect(range) + listOf(this.intersect(range))).filter { !it.isEmpty() }
}