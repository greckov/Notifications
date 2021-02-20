package ua.nure.notes.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface NoteDao {
    @Query("SELECT * FROM note ORDER BY is_primary DESC")
    fun getAllNotes(): LiveData<List<NoteItem>>

    @Query("SELECT * FROM note WHERE id = :note_id")
    suspend fun getNote(note_id: DatabaseId): NoteItem

    @Insert
    suspend fun createNote(noteItem: NoteItem)

    @Query("DELETE FROM note")
    suspend fun deleteAllNotes()
}
