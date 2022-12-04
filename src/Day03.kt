fun main() {
    fun part1(input: List<String>): Long {
        return input.map { Rucksack(it) }.sumOf { it.type.priority }
    }

    fun part2(input: List<String>): Long {
        return input.map { Rucksack(it) }.fold(mutableListOf()) { badges: MutableList<Badge>, rucksack: Rucksack ->
            if (badges.isEmpty() || badges.last().isFull) {
                badges.add(Badge(rucksack))
            } else {
                badges.last().add(rucksack)
            }
            return@fold badges
        }.sumOf { it.type.priority }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157L)
    check(part2(testInput) == 70L)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

class Badge(rucksack: Rucksack) {
    private var rucksacks: List<Rucksack> = listOf(rucksack)

    val isFull: Boolean
        get() = rucksacks.size >= 3

    fun add(rucksack: Rucksack) {
        rucksacks = rucksacks.plus(rucksack)
    }

    val type: Type
        get() = Type(rucksacks.first().content.find { char -> rucksacks.all { it.content.contains(char) } }!!)
}

class Rucksack(val content: String) {
    private val firstCompartment: String
        get() = content.substring(0, content.length / 2)
    private val secondCompartment: String
        get() = content.substring(content.length / 2)

    val type: Type
        get() = Type(firstCompartment.find {
            secondCompartment.contains(it)
        }!!)
}

class Type(private val char: Char) {
    val priority: Long
        get() = if (char.isLowerCase()) {
            (char.code - 'a'.code + 1).toLong()
        } else {
            (char.code - 'A'.code + 27).toLong()
        }
}

