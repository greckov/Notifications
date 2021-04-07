package ua.nure.notifications.ui.view.sender

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.viewmodel.ext.android.viewModel
import ua.nure.notifications.R
import ua.nure.notifications.database.DatabaseId
import ua.nure.notifications.databinding.ActivityViewSenderBinding

private const val ITEM_ID_ARG = "user_id"

class SenderViewActivity : AppCompatActivity() {
    private val vm: SenderViewViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val binding = ActivityViewSenderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        val userId = intent.getLongExtra(ITEM_ID_ARG, 0)
        vm.setOnDataCallback(userId) { user ->
            binding.txtUsername.text = getString(R.string.title_user_name, user.username)
            binding.txtFullname.text = getString(R.string.field_user_fullname, user.fullName)
            binding.txtAge.text = getString(R.string.field_user_age, user.age)
            binding.txtJoinedAt.text = getString(R.string.field_joined, user.createdAtTs.toString())
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        fun toBundle(context: Context, id: DatabaseId) =
            Intent(context, SenderViewActivity::class.java).apply {
                putExtra(ITEM_ID_ARG, id)
            }
    }
}
