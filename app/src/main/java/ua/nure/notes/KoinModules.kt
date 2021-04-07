package ua.nure.notes

import androidx.room.Room
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ua.nure.notes.database.AppDatabase
import ua.nure.notes.ui.main.NotificationListViewModel
import ua.nure.notes.ui.view.notification.NotificationViewViewModel
import ua.nure.notes.ui.view.sender.SenderViewViewModel

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
