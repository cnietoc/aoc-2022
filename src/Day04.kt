fun main() {
    fun getPairsFrom(input: List<String>) = input.map { line ->
        line.split(",").map { rangeRaw ->
            rangeRaw.split("-").let { IntRange(it.first().toInt(), it.last().toInt()) }
        }.let {
            PairOfRanges(it.first(), it.last())
        }
    }

    fun part1(input: List<String>): Int {
        return getPairsFrom(input).count { it.isFullyOverlapped() }
    }

    fun part2(input: List<String>): Int {
        return getPairsFrom(input).count { it.isOverlapped() }
    }


    val testInput = readInput("Day04_test")
    val input = readInput("Day04")

    check(part1(testInput) == 2)
    println(part1(input))

    check(part2(testInput) == 4)
    println(part2(input))
}

class PairOfRanges(private val firstRange: IntRange, private val secondRange: IntRange) {
    fun isFullyOverlapped() =
        firstRange.subtract(secondRange).isEmpty() || secondRange.subtract(firstRange).isEmpty()

    fun isOverlapped() = firstRange.intersect(secondRange).isNotEmpty()
}
