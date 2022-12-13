package com.example.rxnotes.feature_note.domain.use_cases

import com.example.rxnotes.feature_note.domain.models.Note
import com.example.rxnotes.feature_note.domain.repository.NoteRepository
import com.example.rxnotes.feature_note.domain.util.NoteOrderType
import com.example.rxnotes.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase(
    private val repo: NoteRepository
) {
    operator fun invoke(
        noteOrderType: NoteOrderType = NoteOrderType.Title(OrderType.Asc)
    ): Flow<List<Note>> {
        return repo.getNotes().map { notes ->
            when(noteOrderType.orderType) {
                is OrderType.Asc -> {
                    when(noteOrderType) {
                        is NoteOrderType.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteOrderType.Date -> notes.sortedBy { it.timeStamp }
                        is NoteOrderType.Colour -> notes.sortedBy { it.color }
                    }
                }

                is OrderType.Desc -> {
                    when(noteOrderType) {
                        is NoteOrderType.Title -> notes.sortedBy { it.title.uppercase() }
                        is NoteOrderType.Date -> notes.sortedByDescending { it.timeStamp }
                        is NoteOrderType.Colour -> notes.sortedByDescending { it.color }
                    }
                }
            }

        }
    }
}