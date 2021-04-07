package ua.nure.notifications.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserItem::class, NotificationItem::class], exportSchema = false, version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract val userDAO: UserDAO
    abstract val notificationDAO: NotificationDAO
}
