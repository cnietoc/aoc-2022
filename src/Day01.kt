fun main() {
    fun calculateElvesCalories(input: List<String>): MutableList<Long> {
        val elvesCalories = mutableListOf<Long>()
        var index = 0
        input.forEach {
            if (it.isBlank()) {
                index++
            } else {
                if (elvesCalories.getOrNull(index) == null) {
                    elvesCalories.add(index, 0L)
                }
                elvesCalories[index] = elvesCalories[index] + it.toLong()

            }
        }
        return elvesCalories
    }

    fun part1(input: List<String>): Long {
        val elvesCalories = calculateElvesCalories(input)

        return elvesCalories.max()
    }

    fun part2(input: List<String>): Long {
        val elvesCalories = calculateElvesCalories(input)

        return elvesCalories.sortedDescending().take(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000L)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
