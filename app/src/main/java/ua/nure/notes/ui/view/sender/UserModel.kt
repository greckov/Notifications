package ua.nure.notes.ui.view.sender

import ua.nure.notes.database.DatabaseId
import java.util.*

data class UserModel(
    val id: DatabaseId,
    val email: String,
    val username: String,
    val fullName: String,
    val age: Int,
    val createdAtTs: Date
)
