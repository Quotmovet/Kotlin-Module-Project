import java.util.*
class Archives {

    private val scanner = Scanner(System.`in`)
    private val allArchives = mutableListOf<AllArchives>()
    private val workingNotes = WorkingWithNotes(this)
    private val methods = Methods()

    internal fun mainMenu() {
        val title = "Введите значение для выбора необходимого действия: "

        val scope = listOf("Создать архив", "Выбрать архив", "Выйти из программы")

        val actions = listOf(
            { createArchives() },
            { chooseArchives() },
            { methods.exitProgramme() }
        )

        methods.menuPattern(title, scope, actions)
    }

    internal fun subMenu(selectedArchive: AllArchives) {
        val title = "Введите значение для выбора необходимого действия: "

        val scope = listOf("Создать заметку", "Выбрать заметку", "Вернуться назад")

        val actions = listOf(
            { workingNotes.createNote(selectedArchive) },
            { workingNotes.chooseNote() },
            { mainMenu() }
        )

        methods.menuPattern(title, scope, actions)
    }

    private fun createArchives() {
        val nameArchives = methods.inputNonEmptyString("Введите наименование архива: ")

        val newArchive = AllArchives(nameArchives, mutableListOf())
        allArchives.add(newArchive)

        println("Архив '$nameArchives' создан.\n")
        mainMenu()
    }

    private fun chooseArchives() {

        methods.showList(allArchives, "архивов"){
            mainMenu()
        }

        val nameArchives: Int = methods.readInput("Выберите нужный вам архив, введя его номер: ", allArchives)

        val selectedArchive = allArchives[nameArchives - 1]
        println("Выбран архив: '${selectedArchive.name}'")

        if (selectedArchive.noteList.isNotEmpty()) {
            subMenu(selectedArchive)
        } else {
            print("Внутри архива нет заметок.\nВведите '1', чтобы вернуться в главное меню или '2', чтобы сформировать первую заметку: ")
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