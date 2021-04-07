package ua.nure.notifications.ui.view.notification

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.viewmodel.ext.android.viewModel
import ua.nure.notifications.R
import ua.nure.notifications.database.DatabaseId
import ua.nure.notifications.databinding.ActivityViewNotificationBinding
import ua.nure.notifications.ui.view.sender.SenderViewActivity

private const val ITEM_ID_ARG = "notification_id"

class NotificationViewActivity : AppCompatActivity() {
    private val vm: NotificationViewViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val binding = ActivityViewNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val notificationId = intent.getIntExtra(ITEM_ID_ARG, 0)

        vm.setOnDataCallback(notificationId) { notification ->
            binding.txtTitle.text = getString(R.string.title_notification_name, notificationId)
            binding.txtSentBy.text = getString(R.string.field_sent_by, notification.senderUsername)
            binding.txtCreatedAt.text = notification.createdAtTs.toString()
            binding.txtMessage.text = notification.content

            binding.txtSentBy.setOnClickListener {
                startActivity(SenderViewActivity.toBundle(this, notification.senderId))
            }

            binding.btnDeleteAndBack.setOnClickListener {
                vm.deleteNotification(notificationId)
                onBackPressed()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        fun toBundle(context: Context, id: DatabaseId) =
            Intent(context, NotificationViewActivity::class.java).apply {
                putExtra(ITEM_ID_ARG, id)
            }
    }
}
