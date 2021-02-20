package ua.nure.notes.ui.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Database
import org.koin.android.viewmodel.ext.android.viewModel
import ua.nure.notes.R
import ua.nure.notes.database.DatabaseId
import ua.nure.notes.databinding.ActivityNoteListBinding
import ua.nure.notes.ui.add.AddNoteActivity
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
        else -> super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityNoteListBinding.inflate(layoutInflater)

        val listAdapter = NoteListAdapter(onItemClick = this::openViewActivity)

        vm.noteListLiveData.observe(this) { data ->
            listAdapter.submitList(data)
        }

        vm.deleteEnabledLiveData.observe(this) { show ->
            // TODO: Handle clear all button disabling or enabling
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
