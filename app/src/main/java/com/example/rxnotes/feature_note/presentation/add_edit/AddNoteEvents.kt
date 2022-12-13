package com.example.rxnotes.feature_note.presentation.add_edit

import androidx.compose.ui.focus.FocusState

sealed class AddNoteEvents {
    data class EnteredTitle(val value: String): AddNoteEvents()
    data class ChangeTitleFocus(val focus: FocusState): AddNoteEvents()
    data class EnteredContent(val value: String): AddNoteEvents()
    data class ChangeContentFocus(val focus: FocusState): AddNoteEvents()
    data class ChangeColour(val colour: Int): AddNoteEvents()
    object SaveNote: AddNoteEvents()
}
