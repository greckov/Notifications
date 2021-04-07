package ua.nure.notifications.ui.view.notification

import ua.nure.notifications.database.DatabaseId
import java.util.*

data class NotificationModel(
    val id: DatabaseId,
    val senderId: DatabaseId,
    val senderUsername: String,
    val content: String,
    val createdAtTs: Date
)
