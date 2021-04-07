package ua.nure.notifications.ui.view.notification


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ua.nure.notifications.database.DatabaseId
import ua.nure.notifications.database.NotificationDAO
import ua.nure.notifications.database.UserDAO
import java.util.*


class NotificationViewViewModel(
    private val notificationDao: NotificationDAO,
    private val userDao: UserDAO
): ViewModel() {
    private var data: NotificationModel? = null

    fun setOnDataCallback(notificationId: DatabaseId, onData: (notification: NotificationModel) -> Unit) {
        if (data != null) {
            onData(data!!)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val notification = notificationDao.getNotificationById(notificationId)

            if (!notification.isRead) {
                notificationDao.markNotificationAsRead(notificationId)
            }

            val username = userDao.getUsernameById(notification.senderId)
            val model = NotificationModel(
                id = notification.id,
                senderId = notification.senderId,
                senderUsername = username,
                content = notification.content,
                createdAtTs = Date(notification.createdAtTs * 1000)
            )

            withContext(Dispatchers.Main) {
                data = model
                onData(model)
            }
        }
    }

    fun deleteNotification(notificationId: DatabaseId) {
        GlobalScope.launch(Dispatchers.IO) {
            notificationDao.deleteNotificationById(notificationId)
        }
    }
}
