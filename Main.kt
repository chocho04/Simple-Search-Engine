package search
import java.io.File
var data = listOf<String>()
var ii = mutableMapOf<String, MutableList<Int>>()

fun data(inputFile: String) = File(inputFile).readLines()
fun invert() {
    data.forEach { val line = it.split(" ")
        line.forEach { word ->
            if (ii.containsKey(word.lowercase())) {
                ii[word.lowercase()]?.add(data.indexOf(it))
            } else {
                ii[word.lowercase()] = mutableListOf(data.indexOf(it))
            }
        }
    }
}
fun search(strategy: String) {
    val searchTerm = readln().lowercase()
    val searchSet = searchTerm.split(" ").toSet()
    val resultSet = mutableSetOf<String>()
    for (word in searchSet) {
        if (ii.containsKey(word)) {
            ii[word]?.forEach { resultSet.add(data[it]) }
        }
    }
    when (strategy) {
        "ANY" -> { resultSet.forEach { println(it) } }
        "NONE" -> { (data.toSet() subtract resultSet).forEach { println(it) } }
        "ALL" -> { resultSet.forEach { if (it.lowercase().split(" ").containsAll(searchSet)) println(it) } }
    }
}
fun menu() {
    while (true) {
        println("=== Menu ===\n1. Find a person\n2. Print all people\n0. Exit")
        when (readln()) {
            "1" -> {
                println("Select a matching strategy: ALL, ANY, NONE")
                val strategy = readln()
                println("Enter a name or email to search all suitable people.")
                search(strategy)
            }
            "2" -> data.forEach { println(it) }
            "0" -> { println("Bye!"); break }
            else -> println("Incorrect option! Try again.")
        }
    }
}
fun main(args: Array<String>) {
    if (args.contains("--data")) data = data(args[args.indexOf("--data") + 1])
    invert()
    menu()
}
