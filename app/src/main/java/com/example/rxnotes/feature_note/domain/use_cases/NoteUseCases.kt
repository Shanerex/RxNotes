package com.example.rxnotes.feature_note.domain.use_cases

data class NoteUseCases (
    val getNotes: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val insertNoteUseCase: InsertNoteUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase
    )