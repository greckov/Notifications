package ua.nure.notes.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*


@Dao
interface UserDAO {
    @Query("SELECT * FROM users WHERE id=:userId")
    suspend fun getUserById(userId: Int): UserItem

    @Query("SELECT * FROM users ORDER BY created_at_ts LIMIT 1")
    suspend fun getLastCreatedUser(): UserItem?

    @Insert
    suspend fun createNewUser(user: UserItem)

    @Query("SELECT username FROM users WHERE id = :user_id")
    suspend fun getUsernameById(user_id: DatabaseId): String
}

@Dao
interface NotificationDAO {
    @Query("SELECT * FROM notifications")
    fun getAllNotifications(): LiveData<List<NotificationItem>>

    @Query("SELECT * FROM notifications WHERE id = :id")
    suspend fun getNotificationById(id: DatabaseId): NotificationItem

    @Query("DELETE FROM notifications WHERE id = :notificationId")
    suspend fun deleteNotificationById(notificationId: DatabaseId)

    @Insert
    suspend fun createNewNotifications(vararg notification: NotificationItem)

    @Query("UPDATE notifications SET is_read=1 WHERE id = :notificationId")
    suspend fun markNotificationAsRead(notificationId: DatabaseId)
}
