package ua.nure.notes.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface NoteDao {
    @Query("SELECT * FROM note WHERE is_deleted = 0 ORDER BY is_primary DESC ")
    fun getAllNotes(): LiveData<List<NoteItem>>

    @Query("SELECT * FROM note WHERE is_deleted = 1 ORDER BY timestamp DESC")
    suspend fun getAllDeletedNotes(): List<NoteItem>

    @Query("SELECT * FROM note WHERE id = :note_id")
    suspend fun getNote(note_id: DatabaseId): NoteItem

    @Insert
    suspend fun createNote(noteItem: NoteItem)

    @Query("UPDATE note SET is_deleted = 1 WHERE is_deleted = 0")
    suspend fun deleteAllNotes()

    @Query("UPDATE note SET is_deleted = 0 WHERE id in (:ids)")
    suspend fun recoverNotes(ids: List<DatabaseId>)
}
