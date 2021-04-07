package ua.nure.notes.ui.view.sender

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ua.nure.notes.database.DatabaseId
import ua.nure.notes.database.UserDAO
import java.util.*

class SenderViewViewModel(private val userDAO: UserDAO) : ViewModel() {
    private var data: UserModel? = null

    fun setOnDataCallback(userId: DatabaseId, onData: (note: UserModel) -> Unit) {
        if (data != null) {
            onData(data!!)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val user = userDAO.getUserById(userId)
            val model = UserModel(
                id = user.id,
                email = user.email,
                username = user.username,
                fullName = user.fullName,
                age = user.age,
                createdAtTs = Date(user.createdAtTs * 1000)
            )

            withContext(Dispatchers.Main) {
                data = model
                onData(model)
            }
        }
    }
}
