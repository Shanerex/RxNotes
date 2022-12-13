package com.example.rxnotes.feature_note.presentation.notes

import com.example.rxnotes.feature_note.domain.models.Note
import com.example.rxnotes.feature_note.domain.util.NoteOrderType

sealed class NoteEvents {
    data class Order(val noteOrderType: NoteOrderType): NoteEvents()
    object Restore: NoteEvents()
    data class Delete(val note: Note): NoteEvents()
    object ToggleOrderSection: NoteEvents()
}
