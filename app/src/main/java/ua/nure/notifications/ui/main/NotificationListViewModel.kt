package ua.nure.notifications.ui.main

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.nure.notifications.database.*
import ua.nure.notifications.getCurrentUnixTime

class NotificationListViewModel(
    private val notificationDao: NotificationDAO,
    private val userDao: UserDAO
) : ViewModel() {
    private val _allNotificationsLiveData = notificationDao.getAllNotifications()
    private val _filterByNonReadLiveData = MutableLiveData<Boolean>()
    val notificationsLiveData: LiveData<List<NotificationItem>> = Transformations.switchMap(
        _filterByNonReadLiveData
    ) { filterByNonRead ->
        Transformations.map(_allNotificationsLiveData) { dataList ->
            dataList.filter { if (filterByNonRead) !it.isRead else true }
        }
    }

    init {
        _filterByNonReadLiveData.value = false
    }

    fun deleteNotification(notificationId: DatabaseId) {
        viewModelScope.launch(Dispatchers.IO) {
            notificationDao.deleteNotificationById(notificationId)
        }
    }

    fun setFilterOptions(nonRead: Boolean) {
        _filterByNonReadLiveData.value = nonRead
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
                lastUser.id = userDao.createNewUser(lastUser)
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
