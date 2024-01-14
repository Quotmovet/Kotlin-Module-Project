import java.util.*
class WorkingWithNotes (private val archives: Archives){
    private val scanner = Scanner(System.`in`)
    private val methods = Methods()
    private lateinit var selectedArchive: AllArchives
    private val notes: MutableList<Note> = mutableListOf()

    internal fun createNote(selectedArchive: AllArchives) {
        this.selectedArchive = selectedArchive
        val nameNote = methods.inputNonEmptyString("Введите наименование заметки: ")

        println("Заметка '$nameNote' создана.")

        val textOfNote = methods.inputNonEmptyString("Введите текст заметки: ")

        val note = Note(nameNote, textOfNote)
        notes.add(note)
        selectedArchive.noteList.add(note)

        println("Заметка '$nameNote' добавлена в архив '${selectedArchive.name}'.\n")
        archives.subMenu(selectedArchive)
    }

    internal fun chooseNote() {
        if (selectedArchive.noteList.isEmpty()) {
            println("К сожалению, список заметок пуст. Создайте новую заметку.\n")
            archives.subMenu(selectedArchive)
        }

        println("Список заметок:")
        selectedArchive.noteList.forEachIndexed { index, note ->
            println("${index + 1}. ${note.name}")
        }

        val noteIndex: Int = methods.readInput("Введите номер заметки для выбора: ") {
            if (it.isBlank()) {
                println("Номер заметки не может быть пустым. Повторите ввод.")
                null
            } else {
                it.toIntOrNull()?.takeIf { input -> input in 1..notes.size } ?: run {
                        println("Введено некорректное значение, необходимо ввести номер вашей заметки.")
                        null
                    }
            }
        }

        if (noteIndex in 1..notes.size) {
            val selectedNote = notes[noteIndex - 1]
            println("""Выбрана заметка: ${selectedNote.name}
            |${selectedNote.text}
            |-----------------------------------------------------------------------------------
            |Чтобы вернуться в предыдущее меню, введите '1'""".trimMargin())

            var tempBack = scanner.nextLine()
            while (tempBack.trim() != "1") {
                print("Неккоректное значение, введите '1': ")
                tempBack = scanner.nextLine()
            }

            archives.subMenu(selectedArchive)
        } else {
            println("Введено некорректное значение, необходимо ввести номер вашей заметки.")
        }
    }
}