import java.util.*

class Day10 : Solution {

    override fun solve1(input: List<String>): Any {

        val pairs = listOf(
            "()", "[]", "{}", "<>"
        ).associate { it.toCharArray()[0] to it.toCharArray()[1] }

        val wrongChars = mutableListOf<Char>()

        input.forEach { line ->

            val stack = Stack<Char>()
            val wrongChar = line.toCharArray().find { char ->

                if(pairs.containsKey(char)){
                    stack.push(char)
                    false
                }else if(pairs.containsValue(char)){
                    val opening = stack.pop()
                    pairs[opening] != char
                }else{
                    require(false){ "Illegal character" }
                    true
                }

            }

            if(wrongChar != null){
                wrongChars.add(wrongChar)
            }

        }

        val scores = mapOf(
            ')' to 3,
            ']' to 57,
            '}' to 1197,
            '>' to 25137
        )

        return wrongChars.sumOf { scores[it]!! }

    }

    override fun solve2(input: List<String>): Any {

        val pairs = listOf(
            "()", "[]", "{}", "<>"
        ).associate { it.toCharArray()[0] to it.toCharArray()[1] }

        val completionStrings = input.mapNotNull { line ->

            val stack = Stack<Char>()
            val wrongChar = line.toCharArray().find { char ->

                if(pairs.containsKey(char)){
                    stack.push(char)
                    false
                }else if(pairs.containsValue(char)){
                    val opening = stack.pop()
                    pairs[opening] != char
                }else{
                    require(false){ "Illegal character" }
                    true
                }

            }

            if(wrongChar != null){
                null
            }else if(stack.isNotEmpty()){
                stack.reversed().map{ pairs[it]!! }.joinToString("")
            }else{
                null
            }
        }

        val scores = mapOf(
            ')' to 1,
            ']' to 2,
            '}' to 3,
            '>' to 4
        )

        return completionStrings.map {
            it.toCharArray().fold(0L){ acc, c ->
                acc * 5L + scores[c]!!
            }
        }.sorted().let { it[(it.size - 1) / 2] }


    }
}