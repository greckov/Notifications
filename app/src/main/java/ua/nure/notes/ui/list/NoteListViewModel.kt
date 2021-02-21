package ua.nure.notes.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.nure.notes.database.NoteDao

class NoteListViewModel(private val noteDao: NoteDao) : ViewModel() {
    val noteListLiveData = noteDao.getAllNotes()


    fun onDeleteAllItems() {

        viewModelScope.launch(Dispatchers.IO) {
            noteDao.deleteAllNotes()
        }
    }
}
