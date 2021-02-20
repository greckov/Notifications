package ua.nure.notes.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.nure.notes.database.NoteItem
import ua.nure.notes.database.NoteDao

class NoteListViewModel(private val noteDao: NoteDao) : ViewModel() {
    val noteListLiveData = noteDao.getAllNotes()
    private val _deleteEnabledLiveData = MutableLiveData<Boolean>()
    val deleteEnabledLiveData: LiveData<Boolean> = _deleteEnabledLiveData

    init {
        _deleteEnabledLiveData.value = true
    }

    fun onDeleteAllItems() {
        _deleteEnabledLiveData.value = false

        viewModelScope.launch(Dispatchers.IO) {
            noteDao.deleteAllNotes()
            _deleteEnabledLiveData.postValue(true)
        }
    }
}
