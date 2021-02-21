package ua.nure.notes.ui.list.deleted

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ua.nure.notes.database.NoteDao
import ua.nure.notes.database.NoteItem

class DeletedNotesViewModel(private val noteDao: NoteDao) : ViewModel() {
    private val _notesLiveData = MutableLiveData<List<DeletedNoteModel>>()
    val notesLiveData: LiveData<List<DeletedNoteModel>> = _notesLiveData

    init {
        _notesLiveData.value = listOf()
        viewModelScope.launch(Dispatchers.IO) {
            val dbNotes = noteDao.getAllDeletedNotes()
            _notesLiveData.postValue(dbNotes.map(::mapEntityToModel))
        }
    }

    fun onRecoverSelectedNotes() {
        val notesToRecover = _notesLiveData.value!!.filter(DeletedNoteModel::checked)

        viewModelScope.launch(Dispatchers.IO) {
            noteDao.recoverNotes(notesToRecover.map(DeletedNoteModel::id))

            withContext(Dispatchers.Main) {
                val allItems = _notesLiveData.value!!
                _notesLiveData.value = allItems.filter { !it.checked }
            }
        }
    }

    private fun mapEntityToModel(entity: NoteItem) = DeletedNoteModel(
        id = entity.id, text = entity.text
    )

}
