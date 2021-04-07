package ua.nure.notes.ui.view.notification

import ua.nure.notes.database.DatabaseId
import java.util.*

data class NotificationModel(
    val id: DatabaseId,
    val senderId: DatabaseId,
    val senderUsername: String,
    val content: String,
    val createdAtTs: Date
)
