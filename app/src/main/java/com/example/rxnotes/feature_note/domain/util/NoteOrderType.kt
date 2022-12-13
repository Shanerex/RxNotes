package com.example.rxnotes.feature_note.domain.util

sealed class NoteOrderType(val orderType: OrderType) {
    class Title(orderType: OrderType): NoteOrderType(orderType)
    class Colour(orderType: OrderType): NoteOrderType(orderType)
    class Date(orderType: OrderType): NoteOrderType(orderType)

    fun copyNoteOrder(orderType: OrderType): NoteOrderType {
        return when(this) {
            is Title -> Title(orderType)
            is Colour -> Colour(orderType)
            is Date -> Date(orderType)
        }
    }
}
