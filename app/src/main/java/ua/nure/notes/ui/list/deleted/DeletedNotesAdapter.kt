package ua.nure.notes.ui.list.deleted

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ua.nure.notes.databinding.ItemDeletedNoteListBinding

class DeletedNotesAdapter : RecyclerView.Adapter<DeletedNotesAdapter.NoteViewHolder>() {
    var data: List<DeletedNoteModel> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class NoteViewHolder private constructor(
            private val binding: ItemDeletedNoteListBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(noteItem: DeletedNoteModel) {
            binding.txtText.text = noteItem.text
            binding.cbRecover.setOnCheckedChangeListener { _, isChecked ->
                noteItem.checked = isChecked
            }
        }

        companion object {
            fun from(parent: ViewGroup): NoteViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemDeletedNoteListBinding.inflate(layoutInflater, parent, false)
                return NoteViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NoteViewHolder.from(parent)

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount(): Int = data.size
}
