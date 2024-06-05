package com.example.noteapp.database

import androidx.room.*
import com.example.noteapp.model.Note

@Dao
interface NoteDao {
    @Insert
    fun insertNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Query("select * from note_table")
    fun getAllNote(): List<Note>

    @Query("select * from note_table where title_col = :title")
    fun checkNote(title : String) : List<Note>

}