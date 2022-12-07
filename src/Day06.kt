
fun main() {
    fun process(input: String, markerSize: Int): Int {
        val chars = input.iterator()

        var charProcessedCount = 0;
        val charProcessed = ArrayDeque<Char>(markerSize + 1)
        while (chars.hasNext()) {
            charProcessed.add(chars.nextChar())
            charProcessedCount++
            if (charProcessed.size > markerSize) {
                charProcessed.removeFirst()
                if (charProcessed.distinct().size == markerSize)
                    return charProcessedCount
            }
        }
        return charProcessedCount
    }

    fun part1(input: String): Int {
        return process(input, 4)
    }

    fun part2(input: String): Int {
        return process(input, 14)
    }


    val testInput = readInput("Day06_test")
    val input = readInput("Day06")

    check(part1(testInput[0]) == 7)
    check(part1(testInput[1]) == 5)
    check(part1(testInput[2]) == 6)
    check(part1(testInput[3]) == 10)
    check(part1(testInput[4]) == 11)
    println(part1(input.first()))

    check(part2(testInput[0]) == 19)
    check(part2(testInput[1]) == 23)
    check(part2(testInput[2]) == 23)
    check(part2(testInput[3]) == 29)
    check(part2(testInput[4]) == 26)
    println(part2(input.first()))
}
