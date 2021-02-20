package ua.nure.notes.ui.add

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import org.koin.android.viewmodel.ext.android.viewModel
import ua.nure.notes.R
import ua.nure.notes.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {
    private val vm: AddNoteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Show Back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val binding = ActivityAddNoteBinding.inflate(layoutInflater)

        vm.savedLiveData.observe(this) { saved ->
            if (saved) {
                finish()
            }
        }

        vm.textValidLiveData.observe(this) { valid ->
            if (!valid) {
                binding.tilText.error = getText(R.string.error_required_field)
            }
            binding.tilText.isErrorEnabled = !valid
        }

        binding.etText.setText(vm.text)
        binding.etText.doOnTextChanged { text, _, _, _ ->
            vm.onUpdateText(text?.toString().orEmpty())
        }

        binding.btnSave.setOnClickListener {
            vm.onSaveNote()
        }

        binding.cbIsPrimary.setOnCheckedChangeListener { _, isChecked ->
            vm.setPrimaryState(isChecked)
        }

        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        fun toBundle(context: Context) = Intent(context, AddNoteActivity::class.java)
    }
}
