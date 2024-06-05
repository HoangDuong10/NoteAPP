package com.example.noteapp.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.example.noteapp.R
import com.example.noteapp.adapter.NoteAdapter
import com.example.noteapp.database.NoteDatabase
import com.example.noteapp.databinding.ActivityMainBinding
import com.example.noteapp.model.Note

class MainActivity : AppCompatActivity() {
    companion object {
         lateinit var adapter: NoteAdapter
    }

    private var listNote = listOf<Note>()
    private lateinit var activityMainBinding: ActivityMainBinding


    private val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            loadData()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        initToolbar()
        displayListNote()
    }
    private fun onClickAddNote() {
        activityMainBinding.btnMainAddNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun displayListNote() {
        adapter = NoteAdapter()
        activityMainBinding.rclvMainNote.setHasFixedSize(true)
        activityMainBinding.rclvMainNote.layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        activityMainBinding.rclvMainNote.addItemDecoration(itemDecoration)
        activityMainBinding.rclvMainNote.adapter = adapter
        loadData()
        onItemDelete()
        onItemUpdate()
        onClickAddNote()
    }

    private fun onItemDelete(){
        adapter.onClickDelete={ note ->
            showConfirmDeleteNote(note,this)
        }
    }

    private fun loadData(){
        listNote = NoteDatabase.getInstance(context = this).getNoteDao().getAllNote()
        adapter.setNote(listNote)
    }

    private fun onItemUpdate(){
        adapter.onClickUpdate={ note ->
            val intent = Intent(this,UpdateNoteActivity::class.java)
            intent.putExtra(UpdateNoteActivity.KEY_NOTE,note)
            activityResultLauncher.launch(intent)
        }
    }

    private fun showConfirmDeleteNote(note:Note, context: Context) {
        MaterialDialog(this).show {
            title(R.string.app_name)
            message(R.string.msg_delete_note)
            positiveButton(R.string.action_ok) {
                NoteDatabase.getInstance(context).getNoteDao().deleteNote(note)
                adapter.deleteNote(note)
            }
            negativeButton(R.string.action_cancel) {}
        }
    }
    private fun initToolbar() {
        activityMainBinding.toolBar.imgBack.visibility = View.GONE
        activityMainBinding.toolBar.tvTitle.text = getString(R.string.app_name)
    }
}