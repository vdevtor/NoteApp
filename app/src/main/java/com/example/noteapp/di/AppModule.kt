package com.example.noteapp.di

import android.app.Application
import androidx.room.Room
import com.example.noteapp.feature_note.data.data_source.NoteDataBase
import com.example.noteapp.feature_note.data.repository.NoteRepositoryImpl
import com.example.noteapp.feature_note.domain.repository.NoteRepository
import com.example.noteapp.feature_note.domain.use_case.AddNoteUseCase
import com.example.noteapp.feature_note.domain.use_case.DeleteNoteUseCase
import com.example.noteapp.feature_note.domain.use_case.GetNotesUseCase
import com.example.noteapp.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesNotesDataBase(app: Application): NoteDataBase {
        return Room.databaseBuilder(app, NoteDataBase::class.java, NoteDataBase.DATABASE_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun providesNoteRepository(db: NoteDataBase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }


    @Provides
    @Singleton
    fun providesNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotesUseCase(repository),
            deleteNotes = DeleteNoteUseCase(repository),
            addNote = AddNoteUseCase(repository)
        )
    }
}