package ua.nure.notes.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.nure.notes.database.NoteDao
import ua.nure.notes.database.NoteItem
import java.util.concurrent.TimeUnit

class AddNoteViewModel(private val noteDao: NoteDao) : ViewModel() {
    private var isPrimary = false

    private val _textLiveData = MutableLiveData<String>()
    val text get() = _textLiveData.value.orEmpty()
    private val _textValidLiveData = MutableLiveData<Boolean>()
    val textValidLiveData: LiveData<Boolean> = _textValidLiveData

    private val _savedLiveData = MutableLiveData<Boolean>()
    val savedLiveData: LiveData<Boolean> = _savedLiveData

    fun onUpdateText(text: String) {
        _textLiveData.value = text
        _textValidLiveData.value = text != ""
    }

    fun setPrimaryState(isPrimary: Boolean) {
        this.isPrimary = isPrimary
    }

    fun onSaveNote() {
        if (textValidLiveData.value != true) {
            _textValidLiveData.value = false
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            noteDao.createNote(NoteItem(
                timestamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()),
                text = text,
                isPrimary= isPrimary
            ))

            _savedLiveData.postValue(true)
        }
    }
}
