package com.example.rxnotes.feature_note.domain.repository

import com.example.rxnotes.feature_note.domain.models.Note
import kotlinx.coroutines.flow.Flow

//for use cases(fake repo)
interface NoteRepository {
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(note: Note)
    fun getNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Int): Note?
}