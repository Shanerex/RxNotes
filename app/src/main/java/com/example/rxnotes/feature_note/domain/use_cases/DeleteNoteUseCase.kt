package com.example.rxnotes.feature_note.domain.use_cases

import com.example.rxnotes.feature_note.domain.models.Note
import com.example.rxnotes.feature_note.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val repo: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        repo.deleteNote(note)
    }
}