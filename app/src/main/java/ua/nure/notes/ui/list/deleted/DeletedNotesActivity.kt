package ua.nure.notes.ui.list.deleted

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.viewmodel.ext.android.viewModel
import ua.nure.notes.databinding.ActivityDeletedNotesBinding

class DeletedNotesActivity : AppCompatActivity() {
    private val vm: DeletedNotesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Show Back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val binding = ActivityDeletedNotesBinding.inflate(layoutInflater)
        val listAdapter = DeletedNotesAdapter()

        vm.notesLiveData.observe(this) { data ->
            listAdapter.data = data
        }

        binding.listDeletedNotes.adapter = listAdapter
        binding.btnRecoverSelected.setOnClickListener {
            vm.onRecoverSelectedNotes()
        }
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        fun toBundle(context: Context) = Intent(context, DeletedNotesActivity::class.java)
    }
}
