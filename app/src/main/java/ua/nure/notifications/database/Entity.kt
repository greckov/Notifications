package ua.nure.notifications.database

import androidx.room.*


@Entity(tableName = "users")
data class UserItem(
    @PrimaryKey(autoGenerate = true)
    val id: DatabaseId = 0,
    val email: String,
    val username: String,
    @ColumnInfo(name = "full_name")
    val fullName: String,
    val age: Int,
    @ColumnInfo(name = "created_at_ts")
    val createdAtTs: Long
)

@Entity(
    tableName = "notifications",
    foreignKeys = [ForeignKey(
        entity = UserItem::class,
        parentColumns = ["id"],
        childColumns = ["sender_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class NotificationItem(
    @PrimaryKey(autoGenerate = true)
    val id: DatabaseId = 0,
    @ColumnInfo(name = "sender_id", index = true)
    val senderId: Int,
    val content: String,
    @ColumnInfo(name = "created_at_ts")
    val createdAtTs: Long,
    @ColumnInfo(name = "is_read")
    val isRead: Boolean
)
