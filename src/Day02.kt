fun main() {
    fun part1(input: List<String>): Long {
        var sumResult = 0L
        input.forEach {
            val (opponentShapeEncrypted, myShapeEncrypted) = it.split(" ")
            val myShape = myShapeEncrypted.toMyShape()
            val opponentShape = opponentShapeEncrypted.toOpponentShape()
            sumResult += myShape.shapeValue() + myShape.playVs(opponentShape).resultValue()
        }

        return sumResult
    }

    fun part2(input: List<String>): Long {
        var sumResult = 0L
        input.forEach {
            val (opponentShapeEncrypted, myExpectedResultEncrypted) = it.split(" ")
            val myExpectedResult = myExpectedResultEncrypted.toResult()
            val opponentShape = opponentShapeEncrypted.toOpponentShape()
            val myShape = myExpectedResult.whatShapeShouldIPlayVs(opponentShape)

            sumResult += myShape.shapeValue() + myShape.playVs(opponentShape).resultValue()
        }

        return sumResult
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15L)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

enum class Shape {
    ROCK {
        override fun playVs(shape: Shape): Result {
            return when (shape) {
                ROCK -> Result.DRAW
                PAPER -> Result.LOSE
                SCISSORS -> Result.WIN
            }
        }
    },
    PAPER {
        override fun playVs(shape: Shape): Result {
            return when (shape) {
                ROCK -> Result.WIN
                PAPER -> Result.DRAW
                SCISSORS -> Result.LOSE
            }
        }
    },
    SCISSORS {
        override fun playVs(shape: Shape): Result {
            return when (shape) {
                ROCK -> Result.LOSE
                PAPER -> Result.WIN
                SCISSORS -> Result.DRAW
            }
        }
    };

    abstract fun playVs(shape: Shape): Result

    enum class Result {
        WIN, LOSE, DRAW
    }
}

fun String.toOpponentShape(): Shape = when (this) {
    "A" -> Shape.ROCK
    "B" -> Shape.PAPER
    "C" -> Shape.SCISSORS
    else -> {
        throw IllegalArgumentException("WTF is $this??")
    }
}

fun String.toMyShape(): Shape = when (this) {
    "X" -> Shape.ROCK
    "Y" -> Shape.PAPER
    "Z" -> Shape.SCISSORS
    else -> {
        throw IllegalArgumentException("WTF is $this??")
    }
}

fun String.toResult(): Shape.Result = when (this) {
    "X" -> Shape.Result.LOSE
    "Y" -> Shape.Result.DRAW
    "Z" -> Shape.Result.WIN
    else -> {
        throw IllegalArgumentException("WTF is $this??")
    }
}

fun Shape.shapeValue() = when (this) {
    Shape.ROCK -> 1L
    Shape.PAPER -> 2L
    Shape.SCISSORS -> 3L
}

fun Shape.Result.resultValue() = when (this) {
    Shape.Result.WIN -> 6L
    Shape.Result.LOSE -> 0L
    Shape.Result.DRAW -> 3L
}

fun Shape.Result.whatShapeShouldIPlayVs(shape: Shape) = when (this) {
    Shape.Result.WIN -> shape.loseVs()
    Shape.Result.LOSE -> shape.winsVs()
    Shape.Result.DRAW -> shape.drawVs()
}

private fun Shape.loseVs() = Shape.values().first { this.playVs(it) == Shape.Result.LOSE }

private fun Shape.winsVs() = Shape.values().first { this.playVs(it) == Shape.Result.WIN }

private fun Shape.drawVs() = Shape.values().first { this.playVs(it) == Shape.Result.DRAW }
