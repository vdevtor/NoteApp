package com.example.noteapp.feature_note.presentation.note

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.util.NoteOrder

sealed class NotesEvent{
    data class  Order(val order: NoteOrder) : NotesEvent( )
    data class  DeleteNote(val note: Note) : NotesEvent( )
    object RestoreNote : NotesEvent()
    object ToggleOrderSection : NotesEvent()
}
