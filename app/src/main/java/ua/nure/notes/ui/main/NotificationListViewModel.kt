package ua.nure.notes.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.nure.notes.database.*
import ua.nure.notes.getCurrentUnixTime

class NotificationListViewModel(
    private val notificationDao: NotificationDAO,
    private val userDao: UserDAO
) : ViewModel() {
    val notificationsLiveData = notificationDao.getAllNotifications()

    fun deleteNotification(notificationId: DatabaseId) {
        viewModelScope.launch(Dispatchers.IO) {
            notificationDao.deleteNotificationById(notificationId)
        }
    }

    fun setFilterOptions(nonRead: Boolean) {

    }

    fun generateRandomData() {
        viewModelScope.launch(Dispatchers.IO) {
            var lastUser = userDao.getLastCreatedUser()

            if (lastUser == null) {
                lastUser = UserItem(
                    email = "test@example.com",
                    fullName = "Test Person",
                    username = "test",
                    age = 25,
                    createdAtTs = getCurrentUnixTime()
                )
                userDao.createNewUser(lastUser)
            }

            val notificationsToCreate = ArrayList<NotificationItem>(5)

            repeat(5) {
                val timestamp = getCurrentUnixTime()
                notificationsToCreate.add(NotificationItem(
                    senderId = lastUser.id,
                    content = "Some test content ${getCurrentUnixTime()}",
                    createdAtTs = timestamp,
                    isRead = false
                ))
            }

            notificationDao.createNewNotifications(*notificationsToCreate.toTypedArray())
        }
    }
}
