fun main() {
    fun processRoot(input: List<String>): Directory {
        val root = Directory(null, null)
        var currentDirectory = root
        val lastCommand = ArrayDeque<String>()
        val inputIterator = input.iterator()
        while (inputIterator.hasNext() || lastCommand.isNotEmpty()) {
            val commandLine = if (inputIterator.hasNext()) inputIterator.next() else null
            if (commandLine == null || commandLine.startsWith('$')) {
                val command = lastCommand.removeFirstOrNull()
                when {
                    command?.startsWith("$ cd") == true -> {
                        currentDirectory = currentDirectory.goTo(command.split(" ")[2])
                    }

                    command == "$ ls" -> {
                        lastCommand.forEach {
                            val file = it.split(" ")
                            when {
                                file[0].toLongOrNull() != null -> {
                                    currentDirectory.addFile(name = file[1], size = file[0].toLong())
                                }

                                file[0] == "dir" -> {
                                    currentDirectory.addDirectory(file[1])
                                }
                            }
                        }
                    }
                }
                lastCommand.clear()
            }
            if (commandLine != null) lastCommand.addLast(commandLine)
        }
        return root
    }

    fun part1(input: List<String>): Long {
        val root = processRoot(input)

        val sum = root.fold(0L) { sum, currentDirectory ->
            val directorySize = currentDirectory.size()
            return@fold if (directorySize < 100000L) {
                sum + directorySize
            } else sum
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        val root = processRoot(input)
        val deviceSize = 70000000
        val freeSpaceRequired = 30000000
        val directoryToDelete =
            root.filter { root.size() - it.size() < deviceSize - freeSpaceRequired }.minBy { it.size() }
        return directoryToDelete.size()
    }

    val testInput = readInput("Day07_test")
    val input = readInput("Day07")

    check(part1(testInput) == 95437L)
    println(part1(input))

    check(part2(testInput) == 24933642L)
    println(part2(input))
}

class Directory(private val parent: Directory?, private val root: Directory?) : Iterable<Directory> {
    fun goTo(directoryName: String): Directory = when (directoryName) {
        "/" -> root ?: this
        ".." -> parent ?: root ?: this
        else -> dirs.getValue(directoryName)
    }

    fun addFile(name: String, size: Long) {
        files[name] = File(size, this)
    }

    fun addDirectory(name: String) {
        dirs[name] = Directory(this, root)
    }

    private val files: MutableMap<String, File> = mutableMapOf()
    private val dirs: MutableMap<String, Directory> = mutableMapOf()

    override fun iterator(): Iterator<Directory> = DirectoryIterator(this)
    fun size(): Long = files.values.sumOf { it.size } + dirs.values.sumOf { it.size() }

    class DirectoryIterator(private val directory: Directory) : Iterator<Directory> {
        var processed = false;
        var dirsIterator = directory.dirs.values.flatten().iterator()
        override fun hasNext(): Boolean = if (!processed) true
        else dirsIterator.hasNext()

        override fun next(): Directory {
            return if (!processed) {
                processed = true
                directory
            } else {
                dirsIterator.next()
            }
        }
    }
}

data class File(val size: Long, val parent: Directory)
