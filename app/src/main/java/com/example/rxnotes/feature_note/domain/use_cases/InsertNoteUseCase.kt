package com.example.rxnotes.feature_note.domain.use_cases

import com.example.rxnotes.feature_note.domain.models.InvalidNoteException
import com.example.rxnotes.feature_note.domain.models.Note
import com.example.rxnotes.feature_note.domain.repository.NoteRepository

class InsertNoteUseCase(private val repo: NoteRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if(note.title.isBlank()) {
            throw InvalidNoteException("Title cannot be empty")
        }
        if(note.content.isBlank()) {
            throw InvalidNoteException("Content cannot be empty")
        }
        repo.insertNote(note)
    }
}