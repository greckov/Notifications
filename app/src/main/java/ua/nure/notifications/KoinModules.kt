package ua.nure.notifications

import androidx.room.Room
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ua.nure.notifications.database.AppDatabase
import ua.nure.notifications.ui.main.NotificationListViewModel
import ua.nure.notifications.ui.view.notification.NotificationViewViewModel
import ua.nure.notifications.ui.view.sender.SenderViewViewModel

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "app-database").build()
    }

    single {
        get<AppDatabase>().notificationDAO
    }

    single {
        get<AppDatabase>().userDAO
    }
}

val viewModelModule = module {
    viewModel { NotificationListViewModel(get(), get()) }
    viewModel { NotificationViewViewModel(get(), get()) }
    viewModel { SenderViewViewModel(get()) }
}
