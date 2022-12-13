package com.example.rxnotes.feature_note.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rxnotes.ui.theme.*

@Entity(tableName = "notes_table")
data class Note(
    val title: String,
    val content: String,
    val color: Int,
    val timeStamp: Long,
    @PrimaryKey
    val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedOrange, RedPink, BabyBlue, Violet, LightGreen)
    }
}

class InvalidNoteException(msg: String): Exception(msg)
