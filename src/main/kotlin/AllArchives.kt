data class AllArchives(val name: String, val noteList: MutableList<Note>) {
    override fun toString(): String {
        return "Архив: '$name', Заметки: ${noteList.size}"
    }
}
data class Note(val name: String, val text: String) {
    override fun toString(): String {
        return "Заметка: '$name'"
    }
}
