package com.example.rxnotes.feature_note.data.data_source

import androidx.room.*
import com.example.rxnotes.feature_note.domain.models.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes_table")
    fun getNotes(): Flow<List<Note>> //suspend functions cannot should not return a flow

    @Query("SELECT * FROM notes_table WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?


}