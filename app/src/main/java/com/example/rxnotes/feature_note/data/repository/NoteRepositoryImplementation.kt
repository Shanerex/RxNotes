package com.example.rxnotes.feature_note.data.repository

import com.example.rxnotes.feature_note.data.data_source.NoteDao
import com.example.rxnotes.feature_note.domain.models.Note
import com.example.rxnotes.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

//The "real" repository
class NoteRepositoryImplementation(
    private val noteDao: NoteDao
): NoteRepository {
    override suspend fun insertNote(note: Note) {
        return noteDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        return deleteNote(note)
    }

    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return getNoteById(id)
    }

}