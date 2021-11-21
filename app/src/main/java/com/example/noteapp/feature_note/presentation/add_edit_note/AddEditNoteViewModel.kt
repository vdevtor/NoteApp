package com.example.noteapp.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
private val notesUseCases: NoteUseCases
) : ViewModel(){

    private val _noteTitle = mutableStateOf(NoteTextFieldState(hint = "Enter title..."))
    val noteTitle : State<NoteTextFieldState> = _noteTitle



    private val _noteContent= mutableStateOf(NoteTextFieldState(hint = "Enter some content..."))
    val noteContent : State<NoteTextFieldState> = _noteContent

    private val _noteColor= mutableStateOf(Note.noteColors.random().toArgb())
    val noteColor : State<Int> = _noteColor

    private val _eventFlow  = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(){

    }

    sealed class UiEvent{
        data class ShowSnackBar(val message : String) : UiEvent()
        object SaveNote : UiEvent()

    }

}