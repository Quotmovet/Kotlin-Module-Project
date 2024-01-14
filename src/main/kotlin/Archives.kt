import java.util.*
class Archives {
    private val scanner = Scanner(System.`in`)
    private val allArchives = mutableListOf<AllArchives>()
    private val workingNotes = WorkingWithNotes(this)
    private val methods = Methods()

    internal fun mainMenu() {
        val title = "Введите цифру для выбора необходимого действия:"

        val scope = listOf("Создать архив", "Выбрать архив", "Выйти из программы")

        val actions = listOf(
            { createArchives() },
            { chooseArchives() },
            { methods.exitProgramme() }
        )

        methods.menu(title, scope, actions)
    }

    internal fun subMenu(selectedArchive: AllArchives) {
        val title = "Введите цифру для выбора необходимого действия:"

        val scope = listOf("Создать заметку", "Выбрать заметку", "Вернуться назад")

        val actions = listOf(
            { workingNotes.createNote(selectedArchive) },
            { workingNotes.chooseNote() },
            { mainMenu() }
        )

        methods.menu(title, scope, actions)
    }

    private fun createArchives() {
        val nameArchives = methods.inputNonEmptyString("Введите наименование архива: ")

        val newArchive = AllArchives(nameArchives, mutableListOf())
        allArchives.add(newArchive)

        println("Архив '$nameArchives' создан.\n")
        mainMenu()
    }

    private fun chooseArchives() {
        if (allArchives.isEmpty()) {
            println("К сожалению, список архивов пуст. Создайте новый архив.\n")
            mainMenu()
        }

        println("Список архивов:")
        allArchives.forEachIndexed { index, archive ->
            println("${index + 1}. ${archive.name}")
        }

        val nameArchives: Int = methods.readInput("Выберите нужный вам архив, введя его номер: ") {
            if (it.isBlank()) {
                println("Номер архива не может быть пустым. Повторите ввод.")
                null
            } else {
                it.toIntOrNull()?.takeIf { input -> input in 1..allArchives.size }
                    ?: run {
                        println("Введено некорректное значение, необходимо ввести номер вашего архива.")
                        null
                    }
            }
        }

        val selectedArchive = allArchives[nameArchives - 1]
        println("Выбран архив: '${selectedArchive.name}'")

        if (selectedArchive.noteList.isNotEmpty()) {
            subMenu(selectedArchive)
        } else {
            println("Внутри архива нет заметок.\nВведите '1', чтобы вернуться в главное меню или '2', чтобы сформировать первую заметку:")
            var tempInput: String
            while (true) {
                tempInput = scanner.nextLine()
                when (tempInput) {
                    "1" -> mainMenu()
                    "2" -> workingNotes.createNote(selectedArchive)
                    else -> {
                        print("Некорректное значение, введите '1' или '2': ")
                    }
                }
            }
        }
    }
}