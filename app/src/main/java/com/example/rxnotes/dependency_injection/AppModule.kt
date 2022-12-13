package com.example.rxnotes.dependency_injection

import android.content.Context
import androidx.room.Room
import com.example.rxnotes.feature_note.data.data_source.NoteDao
import com.example.rxnotes.feature_note.data.data_source.NoteDatabase
import com.example.rxnotes.feature_note.data.repository.NoteRepositoryImplementation
import com.example.rxnotes.feature_note.domain.repository.NoteRepository
import com.example.rxnotes.feature_note.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "note_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImplementation(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repo: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotesUseCase(repo),
            deleteNoteUseCase = DeleteNoteUseCase(repo),
            insertNoteUseCase = InsertNoteUseCase(repo),
            getNoteByIdUseCase = GetNoteByIdUseCase(repo)
        )
    }
}