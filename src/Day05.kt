fun main() {
    fun createCratesStack(inputIterator: Iterator<String>): CratesStack {
        var line: String = inputIterator.next()
        val cratesRaw: ArrayDeque<String> = ArrayDeque()
        while (line.isNotBlank()) {
            cratesRaw.addFirst(line)
            line = inputIterator.next()
        }

        val lastCrateLine = cratesRaw.removeFirst()
        val cratesNumber = lastCrateLine.split(" ").filterNot { it.isBlank() }
        val cratesStack = CratesStack(cratesNumber.size)
        for (cratesLine in cratesRaw) {
            for (stackNumber in cratesNumber) {
                val crate = cratesLine.getOrNull(lastCrateLine.indexOf(stackNumber))
                if (crate != null && crate.isLetter()) {
                    cratesStack.add(stackNumber.toInt(), crate)
                }
            }
        }
        return cratesStack
    }

    fun part1(input: List<String>): String {
        val inputIterator = input.iterator()
        val cratesStack = createCratesStack(inputIterator)

        for (movement in inputIterator) {
            val movementSplinted = movement.split(" ").mapNotNull { it.toIntOrNull() }
            cratesStack.move(movementSplinted[0], movementSplinted[1], movementSplinted[2])
        }

        return cratesStack.getTopCrates().concatToString()

    }

    fun part2(input: List<String>): String {
        val inputIterator = input.iterator()
        val cratesStack = createCratesStack(inputIterator)

        for (movement in inputIterator) {
            val movementSplinted = movement.split(" ").mapNotNull { it.toIntOrNull() }
            cratesStack.moveOnPack(movementSplinted[0], movementSplinted[1], movementSplinted[2])
        }

        return cratesStack.getTopCrates().concatToString()

    }


    val testInput = readInput("Day05_test")
    val input = readInput("Day05")

    check(part1(testInput) == "CMZ")
    println(part1(input))

    check(part2(testInput) == "MCD")
    println(part2(input))
}

class CratesStack(cratesStackNumber: Int) {
    fun add(stackNumber: Int, crate: Char) {
        stacks[stackNumber - 1].addLast(crate)
    }

    private val stacks = ArrayList<ArrayDeque<Char>>(cratesStackNumber)

    init {
        for (i in 0 until cratesStackNumber) {
            stacks.add(ArrayDeque())
        }
    }

    override fun toString(): String {
        return "CratesStack(stacks=$stacks)"
    }

    fun move(crates: Int, fromStack: Int, toStack: Int) {
        repeat(crates) {
            val crateOnMovement = stacks[fromStack - 1].removeLast()
            stacks[toStack - 1].addLast(crateOnMovement)
        }
    }

    fun moveOnPack(crates: Int, fromStack: Int, toStack: Int) {
        val cratesOnMovement = ArrayDeque<Char>()
        repeat(crates) {
            cratesOnMovement.add(stacks[fromStack - 1].removeLast())
        }
        repeat(crates) {
            stacks[toStack - 1].addLast(cratesOnMovement.removeLast())
        }
    }

    fun getTopCrates(): CharArray = stacks.map { it.last() }.toCharArray()

}
