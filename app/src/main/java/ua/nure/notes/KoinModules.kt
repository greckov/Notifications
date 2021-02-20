package ua.nure.notes

import androidx.room.Room
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ua.nure.notes.database.AppDatabase
import ua.nure.notes.ui.add.AddNoteViewModel
import ua.nure.notes.ui.list.NoteListViewModel
import ua.nure.notes.ui.view.NoteViewViewModel

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "app-database").build()
    }

    single {
        get<AppDatabase>().noteDao()
    }
}

val viewModelModule = module {
    viewModel { NoteListViewModel(get()) }
    viewModel { AddNoteViewModel(get()) }
    viewModel { NoteViewViewModel(get()) }
}
