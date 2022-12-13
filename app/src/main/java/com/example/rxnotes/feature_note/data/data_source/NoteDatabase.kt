package com.example.rxnotes.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rxnotes.feature_note.domain.models.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {
    abstract val noteDao: NoteDao
}