package com.example.noteapp.activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.noteapp.R
import com.example.noteapp.database.NoteDatabase
import com.example.noteapp.databinding.ActivityUpdateNoteBinding
import com.example.noteapp.model.Note
@Suppress("DEPRECATION")
class UpdateNoteActivity : AppCompatActivity() {
    companion object{
        const val KEY_NOTE = "object note"
    }

    private lateinit var activityUpdateNoteBinding : ActivityUpdateNoteBinding
    private var note : Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityUpdateNoteBinding= ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(activityUpdateNoteBinding.root)
        initToolbar()
        getNote()
        activityUpdateNoteBinding.btnUpdateNote.setOnClickListener {
           updateNote()
        }

    }

    private fun updateNote(){
        if (note != null) {
            note!!.title = activityUpdateNoteBinding.edtTitle.text.toString()
            note!!.description = activityUpdateNoteBinding.edtDescrption.text.toString()
        }
        note?.let { it1 -> NoteDatabase.getInstance(this).getNoteDao().updateNote(it1) }
        val intent = Intent()
        setResult(RESULT_OK,intent)
        finish()
    }
    private fun getNote() {
        note = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(KEY_NOTE, Note::class.java)
        } else {
            intent.getSerializableExtra(KEY_NOTE) as Note
        }
        if(note!= null){
            activityUpdateNoteBinding.edtTitle.setText(note!!.title)
            activityUpdateNoteBinding.edtDescrption.setText(note!!.description)

        }
    }

    private fun initToolbar() {
        activityUpdateNoteBinding.toolBar.imgBack.visibility = View.VISIBLE
        activityUpdateNoteBinding.toolBar.tvTitle.text = getString(R.string.update_note)
        activityUpdateNoteBinding.toolBar.imgBack.setOnClickListener { finish() }
    }
}