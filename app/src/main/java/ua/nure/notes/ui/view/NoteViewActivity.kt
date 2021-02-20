package ua.nure.notes.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.viewmodel.ext.android.viewModel
import ua.nure.notes.database.DatabaseId
import ua.nure.notes.databinding.ActivityViewNoteBinding

private const val ITEM_ID_ARG = "item_id"

class NoteViewActivity : AppCompatActivity() {
    private val vm: NoteViewViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Show Back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val binding = ActivityViewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val noteId = intent.getIntExtra(ITEM_ID_ARG, 0)

        vm.setOnDataCallback(noteId) { note ->
            binding.txtNoteTitle.text = "Note №${note.id}"
            binding.txtNoteText.text = note.text
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        fun toBundle(context: Context, id: DatabaseId) =
            Intent(context, NoteViewActivity::class.java).apply {
                putExtra(ITEM_ID_ARG, id)
            }
    }
}
