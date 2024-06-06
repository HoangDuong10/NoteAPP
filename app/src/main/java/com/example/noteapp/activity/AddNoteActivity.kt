package com.example.noteapp.activity
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.noteapp.R
import com.example.noteapp.database.NoteDatabase
import com.example.noteapp.databinding.ActivityAddNoteBinding
import com.example.noteapp.model.Note


class AddNoteActivity : AppCompatActivity() {
    private lateinit var activityAddNoteBinding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAddNoteBinding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(activityAddNoteBinding.root)
        initToolbar()
        onClickAddNote()
    }

    private fun isNoteExits(note: Note): Boolean {
        val list = NoteDatabase.getInstance(this).getNoteDao().checkNote(note.title)
        return list.isNotEmpty()
    }

    private fun onClickAddNote() {
        activityAddNoteBinding.btnAddNote.setOnClickListener {
            if (activityAddNoteBinding.edtTitle.text.isEmpty() || activityAddNoteBinding.edtDescrption.text.isEmpty()) {
                Toast.makeText(this, "Note empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val note = Note(
                activityAddNoteBinding.edtTitle.text.toString(),
                activityAddNoteBinding.edtDescrption.text.toString()
            )
            if (isNoteExits(note)) {
                Toast.makeText(this, "Note exits", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            NoteDatabase.getInstance(this).getNoteDao().insertNote(note)
            MainActivity.adapter.addNote(note)
            finish()
        }

    }

    private fun initToolbar() {
        activityAddNoteBinding.toolBar.imgBack.visibility = View.VISIBLE
        activityAddNoteBinding.toolBar.tvTitle.text = getString(R.string.add_note)
        activityAddNoteBinding.toolBar.imgBack.setOnClickListener { finish() }
    }


}