package ua.nure.notes.ui.list
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Database
import ua.nure.notes.database.DatabaseId
import ua.nure.notes.database.NoteItem
import ua.nure.notes.databinding.ItemNoteListBinding


private typealias OnClickListener = (itemId: DatabaseId) -> Unit

class NoteListAdapter(
        private val onItemClick: OnClickListener
) : ListAdapter<NoteItem, NoteListAdapter.NoteViewHolder>(NoteListDiffCallback()) {
    class NoteViewHolder private constructor(
        private val binding: ItemNoteListBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(noteItem: NoteItem, onItemClick: OnClickListener) {
            binding.txtText.text = noteItem.text
            binding.txtText.setOnClickListener { onItemClick(noteItem.id) }
        }

        companion object {
            fun from(parent: ViewGroup): NoteViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNoteListBinding.inflate(layoutInflater, parent, false)
                return NoteViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }
}

class NoteListDiffCallback: DiffUtil.ItemCallback<NoteItem>() {
    override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean =
        oldItem.text == newItem.text && oldItem.timestamp == newItem.timestamp
}
