import kotlin.system.exitProcess
import java.util.*
class Methods {
    val scanner = Scanner(System.`in`)

    // Паттерн меню
    fun menu(title: String, scope: List<String>, actions: List<() -> Unit>) {
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

    // Проверка на пустую строку
    fun inputNonEmptyString(prompt: String): String {
        var userInput: String

        do {
            print(prompt)
            userInput = scanner.nextLine()

            if (userInput.isBlank()) {
                println("Введенная строка не может быть пустой. Повторите ввод.")
            }
        } while (userInput.isBlank())

        return userInput
    }

    // Проверка на Int и пустую строку
    inline fun <reified T> readInput(prompt: String, errorMessage: String? = null, crossinline converter: (String) -> T?): T {
        var userInput: String

        do {
            print(prompt)
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