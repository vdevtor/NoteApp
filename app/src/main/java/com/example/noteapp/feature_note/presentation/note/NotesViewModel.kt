package com.example.noteapp.feature_note.presentation.note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.use_case.NoteUseCases
import com.example.noteapp.feature_note.domain.util.NoteOrder
import com.example.noteapp.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesUseCase: NoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NoteStates())
    val state: State<NoteStates> = _state

    private var recentlyDeletedNote: Note? = null
    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                if (state.value.noteOrder::class == event.order::class &&
                    state.value.noteOrder.orderType == event.order.orderType){
                    return
                }
                getNotes(event.order)
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    notesUseCase.deleteNotes(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NotesEvent.RestoreNote -> {
                     viewModelScope.launch {
                         notesUseCase.addNote(recentlyDeletedNote ?: return@launch)
                         recentlyDeletedNote = null
                     }
            }

            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(order: NoteOrder) {
        getNotesJob?.cancel()

       getNotesJob = notesUseCase.getNotes(order)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = order
                )
            }
            .launchIn(viewModelScope)
    }
}