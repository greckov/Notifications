package ua.nure.notifications.ui.view.sender

import ua.nure.notifications.database.DatabaseId
import java.util.*

data class UserModel(
    val id: DatabaseId,
    val email: String,
    val username: String,
    val fullName: String,
    val age: Int,
    val createdAtTs: Date
)
