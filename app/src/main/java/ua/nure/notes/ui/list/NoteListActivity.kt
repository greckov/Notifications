package ua.nure.notes.ui.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.viewmodel.ext.android.viewModel
import ua.nure.notes.R
import ua.nure.notes.database.DatabaseId
import ua.nure.notes.databinding.ActivityNoteListBinding
import ua.nure.notes.ui.add.AddNoteActivity
import ua.nure.notes.ui.list.deleted.DeletedNotesActivity
import ua.nure.notes.ui.view.NoteViewActivity

class NoteListActivity: AppCompatActivity() {
    private val vm: NoteListViewModel by viewModel()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_primary, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.btn_delete_all_notes -> {
            vm.onDeleteAllItems()
            true
        }
        R.id.btn_deleted_notes -> {
            startActivity(DeletedNotesActivity.toBundle(this))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityNoteListBinding.inflate(layoutInflater)

        val listAdapter = NoteListAdapter(onItemClick = this::openViewActivity)

        vm.noteListLiveData.observe(this) { data ->
            listAdapter.submitList(data)
        }

        binding.listNotes.adapter = listAdapter
        binding.btnAddNote.setOnClickListener {
            startActivity(AddNoteActivity.toBundle(this))
        }

        setContentView(binding.root)
    }

    private fun openViewActivity(itemId: DatabaseId) {
        startActivity(NoteViewActivity.toBundle(this, itemId))
    }
}
