package ua.nure.notifications.ui.main
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ua.nure.notifications.database.DatabaseId
import ua.nure.notifications.database.NotificationItem
import ua.nure.notifications.databinding.ItemNotificationListBinding


private typealias ClickHandler = (id: DatabaseId) -> Unit

class NotificationListAdapter(
        private val onItemClick: ClickHandler,
        private val onDeleteClick: ClickHandler
) : RecyclerView.Adapter<NotificationListAdapter.NotificationViewHolder>() {
    var data: List<NotificationItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class NotificationViewHolder private constructor(
        private val binding: ItemNotificationListBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            notificationItem: NotificationItem,
            onItemClick: ClickHandler,
            onDeleteClick: ClickHandler
        ) {
            binding.txtNotificationContent.text = notificationItem.content
            binding.groupNotificationItem.setOnClickListener { onItemClick(notificationItem.id) }
            binding.btnDelete.setOnClickListener { onDeleteClick(notificationItem.id) }
        }

        companion object {
            fun from(parent: ViewGroup): NotificationViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNotificationListBinding.inflate(layoutInflater, parent, false)
                return NotificationViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NotificationViewHolder.from(parent)

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) =
        holder.bind(data[position], onItemClick, onDeleteClick)

    override fun getItemCount() = data.size
}
