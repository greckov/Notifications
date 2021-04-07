package ua.nure.notes.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.bind
import org.koin.android.viewmodel.ext.android.viewModel
import ua.nure.notes.database.DatabaseId
import ua.nure.notes.databinding.ActivityNotificationListBinding
import ua.nure.notes.ui.view.notification.NotificationViewActivity


class NotificationListActivity: AppCompatActivity() {
    private val vm: NotificationListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityNotificationListBinding.inflate(layoutInflater)

        val listAdapter = NotificationListAdapter(
            onItemClick = this::openViewActivity,
            onDeleteClick = vm::deleteNotification
        )

        vm.notificationsLiveData.observe(this) { data ->
            listAdapter.data = data
        }

        binding.cbOnlyNonRead.setOnCheckedChangeListener { _, isChecked ->
            vm.setFilterOptions(nonRead = isChecked)
        }

        binding.btnGenerateRandomData.setOnClickListener {
            vm.generateRandomData()
        }

        binding.listNotifications.adapter = listAdapter

        setContentView(binding.root)
    }

    private fun openViewActivity(itemId: DatabaseId) {
        startActivity(NotificationViewActivity.toBundle(this, itemId))
    }
}
