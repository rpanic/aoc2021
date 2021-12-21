import java.math.BigInteger
import kotlin.math.pow

class Day16 : Solution {

    open class Packet(val version: Int, val type: Int)

    class NumericPacket(version: Int, type: Int, val number: Int) : Packet(version, type)
    class OperatorPacket(version: Int, type: Int, val children: List<Packet>) : Packet(version, type)

    override fun solve1(input: List<String>): Any {
//
//        var bin = BigInteger(input.first(), 16).toString(2).toCharArray().map { it != '0' }.toBooleanArray()
//        if(bin.size % 4 != 0){
//            val missing = 4 - (bin.size % 4) +
//                input.first().count { it == '0' } * 4
//            bin = (1..missing).map { false }.toBooleanArray() + bin
//        }
        val bin = hexToBin(input.first())
        val (packets, _) = bin.decodePacket()

        return packets.sumVersions()
    }

    fun Packet.sumVersions() : Int {
        return if(this is NumericPacket){
            this.version
        }else if(this is OperatorPacket){
            this.version + children.sumOf { it.sumVersions() }
        }else{
            0
        }
    }

    fun BooleanArray.decodePacket(offset: Int = 0) : Pair<Packet, Int>{

        val version = this.sliceArray(offset until offset + 3).toInt()
        val type = this.sliceArray(offset+3 until offset + 6).toInt()
        var newOffset = offset + 6

        return if(type == 4){
            var c = true
            val acc = mutableListOf<Boolean>()
            while(c){
                val window = this.slice(newOffset until newOffset + 5)
                c = window[0]
                acc.addAll(window.subList(1, 5))
                newOffset += 5
            }
            NumericPacket(version, type, acc.toBooleanArray().toInt())
        }else{
            val lengthId = this[newOffset]
            newOffset++
            val childrenList =
            if(!lengthId){ // 0
                val numBits = this.sliceArray(newOffset until newOffset + 15).toInt()
                newOffset += 15
                val subArr = this.sliceArray(newOffset until newOffset + numBits)
                newOffset += numBits
                var subOffset = 0
                val children = mutableListOf<Packet>()
                while(subOffset < numBits){
                    val (child, off) = subArr.decodePacket(subOffset)
                    subOffset += off
                    children += child
                }
                children
            }else{

                val numPackets = this.sliceArray(newOffset until newOffset + 11).toInt()
                newOffset += 11
                val children = mutableListOf<Packet>()
                for(i in 0 until numPackets){
                    val (child, off) = this.decodePacket(newOffset)
                    newOffset = off
                    children += child
                }

                children
            }
            OperatorPacket(version, type, childrenList)
        } to newOffset

    }

    fun BooleanArray.toInt() : Int {
        var byte = 0
        for(i in this.indices){
            if(this[i]) {
                byte += 2.0.pow(size - i - 1).toInt()
            }
        }
        return byte
    }

    override fun solve2(input: List<String>): Any {
        TODO("Not yet implemented")
    }

    private fun hexToBin(hex1: String): BooleanArray {
        var hex = hex1
        hex = hex.replace("0".toRegex(), "0000")
        hex = hex.replace("1".toRegex(), "0001")
        hex = hex.replace("2".toRegex(), "0010")
        hex = hex.replace("3".toRegex(), "0011")
        hex = hex.replace("4".toRegex(), "0100")
        hex = hex.replace("5".toRegex(), "0101")
        hex = hex.replace("6".toRegex(), "0110")
        hex = hex.replace("7".toRegex(), "0111")
        hex = hex.replace("8".toRegex(), "1000")
        hex = hex.replace("9".toRegex(), "1001")
        hex = hex.replace("A".toRegex(), "1010")
        hex = hex.replace("B".toRegex(), "1011")
        hex = hex.replace("C".toRegex(), "1100")
        hex = hex.replace("D".toRegex(), "1101")
        hex = hex.replace("E".toRegex(), "1110")
        hex = hex.replace("F".toRegex(), "1111")
        return hex.map { it == '1' }.toBooleanArray()
    }
}