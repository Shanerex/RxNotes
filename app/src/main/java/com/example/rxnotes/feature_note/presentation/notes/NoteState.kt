package com.example.rxnotes.feature_note.presentation.notes

import com.example.rxnotes.feature_note.domain.models.Note
import com.example.rxnotes.feature_note.domain.util.NoteOrderType
import com.example.rxnotes.feature_note.domain.util.OrderType

data class NoteState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrderType = NoteOrderType.Title(OrderType.Asc),
    val isOrderSectionVisible: Boolean = false
)
