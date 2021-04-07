package ua.nure.notifications.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.viewmodel.ext.android.viewModel
import ua.nure.notifications.SpacingItemDecoration
import ua.nure.notifications.database.DatabaseId
import ua.nure.notifications.databinding.ActivityNotificationListBinding
import ua.nure.notifications.ui.view.notification.NotificationViewActivity


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

        binding.listNotifications.apply {
            adapter = listAdapter
            addItemDecoration(SpacingItemDecoration(10))
        }

        setContentView(binding.root)
    }

    private fun openViewActivity(itemId: DatabaseId) {
        startActivity(NotificationViewActivity.toBundle(this, itemId))
    }
}
