package ua.nure.notes.ui.list.deleted

import ua.nure.notes.database.DatabaseId

data class DeletedNoteModel(
        val id: DatabaseId,
        var checked: Boolean = false,
        val text: String
)