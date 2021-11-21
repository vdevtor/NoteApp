package com.example.noteapp.feature_note.domain.use_case

data class NoteUseCases(
    val getNotes : GetNotesUseCase,
    val deleteNotes : DeleteNoteUseCase,
    val addNote : AddNoteUseCase
)
