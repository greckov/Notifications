package ua.nure.notes.ui.view


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ua.nure.notes.database.DatabaseId
import ua.nure.notes.database.NoteDao
import ua.nure.notes.database.NoteItem

class NoteViewViewModel(private val noteDao: NoteDao): ViewModel() {
    fun setOnDataCallback(noteId: DatabaseId, onData: (note: NoteItem) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val note = noteDao.getNote(noteId)

            withContext(Dispatchers.Main) {
                onData(note)
            }
        }
    }
}
