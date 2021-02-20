package ua.nure.notes.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteItem::class], exportSchema = false, version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
