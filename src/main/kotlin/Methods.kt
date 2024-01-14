import kotlin.system.exitProcess
import java.util.*
class Methods {

    val scanner = Scanner(System.`in`)

    // Паттерн меню
    fun menuPattern(title: String, scope: List<String>, actions: List<() -> Unit>) {
        var intValue: Int?

        do {
            println(title.trimMargin())
            scope.forEachIndexed { index, option ->
                println("${index + 1}. $option")
            }
            print("Ввод: ")

            intValue = readlnOrNull()?.toIntOrNull()

            if (intValue !in 1..scope.size) {
                println("Введено некорректное значение.\n")
            }
        } while (intValue !in 1..scope.size)

        actions[intValue!! - 1]()
    }

    // Демонстрация списка
    fun showList(unit: List<Any>, unitLabel: String, actionIfEmpty: () -> Unit) {
        if (unit.isEmpty()) {
            println("К сожалению, список $unitLabel пуст.\n")
            actionIfEmpty()
            println()
            return
        }

        println("Список $unitLabel:")
        unit.forEachIndexed { index, item ->
            println("${index + 1}. $item")
        }
    }

    // Ввод номера списка
    fun readInput(phrase: String, unitList: List<Any>): Int {
        return readInput(phrase) {
            if (it.isBlank()) {
                println("Номер не может быть пустым. Повторите ввод.")
                null
            } else {
                it.toIntOrNull()?.takeIf { input -> input in 1..unitList.size }
                    ?: run {
                        println("Введено некорректное значение, необходимо ввести номер.")
                        null
                    }
            }
        }
    }

    // Проверка на пустую строку
    fun inputNonEmptyString(phrase: String): String {
        var userInput: String

        do {
            print(phrase)
            userInput = scanner.nextLine()

            if (userInput.isBlank()) {
                println("Введенная строка не может быть пустой. Повторите ввод.")
            }
        } while (userInput.isBlank())

        return userInput
    }

    // Проверка на Int и пустую строку
    inline fun <reified T> readInput(phrase: String, errorMessage: String? = null, crossinline converter: (String) -> T?): T {
        var userInput: String

        do {
            print(phrase)
            userInput = scanner.nextLine()

            if (userInput.isBlank()) {
                println("Введенная строка не может быть пустой. Повторите ввод.")
            } else {
                val convertedValue = converter(userInput)
                if (convertedValue == null) {
                    errorMessage?.let { println(it) }
                } else return convertedValue
            }
        } while (true)
    }

    // Выход из программы
    fun exitProgramme(){
        println("Приложение закрыто")
        exitProcess(0)
    }
}