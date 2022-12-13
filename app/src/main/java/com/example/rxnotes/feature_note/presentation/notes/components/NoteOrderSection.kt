package com.example.rxnotes.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rxnotes.feature_note.domain.util.NoteOrderType
import com.example.rxnotes.feature_note.domain.util.OrderType

@Composable
fun NoteOrderSection(
    modifier: Modifier = Modifier,
    noteOrderType: NoteOrderType = NoteOrderType.Title(OrderType.Asc),
    onOrderChange: (NoteOrderType) -> Unit //pass new note order
) {
    Column(
        modifier = Modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Title",
                selected = noteOrderType is NoteOrderType.Title,
                onSelect = { onOrderChange(NoteOrderType.Title(noteOrderType.orderType)) }
            )

            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Date",
                selected = noteOrderType is NoteOrderType.Date,
                onSelect = { onOrderChange(NoteOrderType.Date(noteOrderType.orderType)) }
            )

            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Colour",
                selected = noteOrderType is NoteOrderType.Colour,
                onSelect = { onOrderChange(NoteOrderType.Colour(noteOrderType.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = noteOrderType.orderType is OrderType.Asc,
                onSelect = { onOrderChange(noteOrderType.copyNoteOrder(OrderType.Asc)) }
            )

            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Descending",
                selected = noteOrderType.orderType is OrderType.Desc,
                onSelect = { onOrderChange(noteOrderType.copyNoteOrder(OrderType.Desc)) }
            )
        }
    }
    
}