import utils.Vector2D
import java.util.*
import kotlin.math.ceil
import kotlin.math.floor

class Day18 : Solution {

    interface Pair{
        fun magnitude() : Int

        fun deepCopy() : Pair
    }

    class RegularValue(var value: Int) : Pair{
        override fun magnitude(): Int {
            return value
        }

        override fun toString(): String {
            return "$value"
        }

        override fun deepCopy(): Pair {
            return RegularValue(value)
        }

    }

    class NestedPair(var first: Pair, var second: Pair) : Pair {
        override fun magnitude(): Int {
            return first.magnitude() * 3 + second.magnitude() * 2
        }

        fun isHoldingOnlyValues() : Boolean{
            return first is RegularValue && second is RegularValue
        }

        override fun toString(): String {
            return "[$first, $second]"
        }

        override fun deepCopy(): Pair {
            return NestedPair(first.deepCopy(), second.deepCopy())
        }

    }

    class PlaceHolderPair() : Pair{
        override fun magnitude(): Int {
            return -1
        }

        override fun deepCopy(): Pair {
            return this
        }
    }

    fun parsePair(input: String) : Pair {

        val stack = Stack<NestedPair>()
        val stringStack = Stack<Char>()
        input.toCharArray().reversed().forEach { stringStack.push(it) }
        var outerMostPair: Pair? = null
        var currentPair: NestedPair? = null

        while(stringStack.isNotEmpty()){
            val c = stringStack.pop()
            if(c == '['){
                    val p = NestedPair(PlaceHolderPair(), PlaceHolderPair())
                    if(outerMostPair == null) outerMostPair = p
                    else stack.push(currentPair)
                    currentPair = p
            }else if(c.isDigit()){
                if(currentPair!!.first !is PlaceHolderPair){
                    currentPair.second = RegularValue(c.digitToInt())
                }else{
                    currentPair.first = RegularValue(c.digitToInt())
                    stringStack.pop() // ','
                }
            }else if(c == ']'){
                val child = currentPair!!
                if(stack.isEmpty()) continue
                currentPair = stack.pop()
                if(currentPair.first is PlaceHolderPair){
                    currentPair.first = child
                }else{
                    currentPair.second = child
                }
            }

        }
        return outerMostPair!!

    }

    fun reduceNumber(number: Pair) {

        fun doReduce(explode: Boolean) : Boolean{

            val stack = Stack<kotlin.Pair<Pair, Int>>()
            stack.push(number to 1)

            val parents = mutableMapOf<Pair, Pair>()
            var lastValue : RegularValue? = null
            var rightValue: Int? = null

            while(stack.isNotEmpty()){

                val (c, level) = stack.pop()
                if(c is NestedPair){

                    if(explode && rightValue == null && level > 4 && c.isHoldingOnlyValues()){
                        //Do explode

                        /*
                        fun findLeft(current: Pair, source: Pair) : Pair?{
                            if(current is RegularValue){
                                return current
                            }else if(current is NestedPair){
                                if(current.first != source){
                                    val next = findLeft(current.first, current)
                                    if(next != null) return next
                                }else if(current.second != source){
                                    val next = findLeft(current.first, current)
                                    if(next != null) return next
                                }
                            }
                        }

                        //Find leftmost
                        var current = parents[c] as NestedPair
                        var prev = c
                        while(true){
                            if(current.first == prev){
                                val prevS = prev
                                prev = current
                                current = parents[prevS] as NestedPair
                            }else if(current.first is RegularValue){
                                (current.first as RegularValue).value = (current.first as RegularValue).value + (c.first as RegularValue).value
                                break
                            }else if(current.first is NestedPair){
                                val nested = current.first as NestedPair
                                current = nested
                            }else{
                                require(false)
                                val prevS = prev
                                prev = current
                                current = parents[prevS] as NestedPair
                            }
                        }

                        //Find rightmost
                        current = parents[c] as NestedPair
                        prev = c
                        while(true){
                            if(current.second == prev){
                                val prevS = prev
                                prev = current
                                current = parents[prevS] as NestedPair
                            }else if(current.second is RegularValue){
                                (current.second as RegularValue).value = (current.second as RegularValue).value + (c.second as RegularValue).value
                                break
                            }else if(current.second is NestedPair){
                                val nested = current.second as NestedPair
                                current = nested
                            }else{
                                require(false)
                                val prevS = prev
                                prev = current
                                current = parents[prevS] as NestedPair
                            }
                        }*/

                        if(lastValue != null){
                            lastValue.value += (c.first as RegularValue).value
                        }
                        rightValue = (c.second as RegularValue).value
                        val parent = parents[c]!! as NestedPair
                        if(parent.first == c){
                            parent.first = RegularValue(0)
                        }else{
                            parent.second = RegularValue(0)
                        }

                    }else {
                        parents[c.first] = c
                        parents[c.second] = c
                        stack.push(c.second to level + 1)
                        stack.push(c.first to level + 1)
                    }

                }else if(c is RegularValue){
                    if(!explode && c.value >= 10){

                        val parent = parents[c]!! as NestedPair
                        val newPair = NestedPair(RegularValue(floor(c.value.toDouble() / 2.0).toInt()), RegularValue(
                            ceil(c.value.toDouble() / 2.0).toInt()))

                        if(parent.first == c){
                            parent.first = newPair
                        }else{
                            parent.second = newPair
                        }
                        return true

                    }
                    lastValue = c
                    if(rightValue != null){
                        c.value += rightValue
                        return true
                    }
                }

            }
            return false

        }

        while(doReduce(true) || doReduce(false)){
            println(number)
        }

    }

    override fun solve1(input: List<String>): Any {

        val res = input.map { parsePair(it) }.reduce { p1, p2 ->
            val added = NestedPair(p1, p2)

            reduceNumber(added)
            added
        }
        println(res)

        return res.magnitude()
    }

    override fun solve2(input: List<String>): Any {

        val x = input.map { parsePair(it) }
        return x.cartesian(x).map { (a, b) ->
            val added = NestedPair(a.deepCopy(), b.deepCopy())
            reduceNumber(added)
            added.magnitude()
        }.maxOrNull()!!

    }
}