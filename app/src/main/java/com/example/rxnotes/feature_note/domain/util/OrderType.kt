package com.example.rxnotes.feature_note.domain.util

sealed class OrderType {
    object Asc: OrderType()
    object Desc: OrderType()
}
