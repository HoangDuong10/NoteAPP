package com.example.noteapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.databinding.ItemNoteBinding
import com.example.noteapp.model.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var listNote: MutableList<Note> = mutableListOf()
    var onClickDelete: ((note: Note) -> Unit)? = null
    var onClickUpdate: ((note: Note) -> Unit)? = null

    class NoteViewHolder(val itemNoteBinding : ItemNoteBinding) : RecyclerView.ViewHolder(itemNoteBinding.root) {
        fun onBind(note: Note) {
            itemNoteBinding.tvNoteTitle.text = note.title
            itemNoteBinding.tvNoteDescrption.text = note.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemNoteBinding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteViewHolder(itemNoteBinding)
    }

    override fun getItemCount(): Int = listNote.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.onBind(listNote[position])
        holder.itemNoteBinding.ivNoteDelete.setOnClickListener {
            onClickDelete?.invoke(listNote[position])
        }

        holder.itemNoteBinding.ivNoteUpdate.setOnClickListener {
            onClickUpdate?.invoke(listNote[position])
        }
    }

    fun setNote(listNote: List<Note>) {
        this.listNote = listNote.toMutableList()
        notifyDataSetChanged()
    }

    fun addNote(note: Note) {
        this.listNote.add(note)
        notifyItemRangeInserted(listNote.size,1)
    }

    fun deleteNote(note : Note) {
        this.listNote.remove(note)
        notifyDataSetChanged()
    }


}