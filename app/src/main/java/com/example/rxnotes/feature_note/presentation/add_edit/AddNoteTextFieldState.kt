package com.example.rxnotes.feature_note.presentation.add_edit

data class AddNoteTextFieldState (
    val text: String = "",
    val isHintVisible: Boolean = true,
    val hint: String = ""
)