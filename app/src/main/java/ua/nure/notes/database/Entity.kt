package ua.nure.notes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class NoteItem(
    @PrimaryKey(autoGenerate = true)
    val id: DatabaseId = 0,
    val timestamp: Long,
    val text: String,
    @ColumnInfo(name = "is_primary")
    val isPrimary: Boolean,
    @ColumnInfo(name = "is_deleted")
    val isDeleted: Boolean = false
)
