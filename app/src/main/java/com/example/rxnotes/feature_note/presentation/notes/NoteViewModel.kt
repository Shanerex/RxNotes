package com.example.rxnotes.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rxnotes.feature_note.domain.models.Note
import com.example.rxnotes.feature_note.domain.use_cases.NoteUseCases
import com.example.rxnotes.feature_note.domain.util.NoteOrderType
import com.example.rxnotes.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
): ViewModel() {

    private val _state = mutableStateOf(NoteState())
    val state: State<NoteState> = _state
    private var recentlyDeleted: Note? = null
    private var getNotesJob: Job? = null
    fun onEvent(events: NoteEvents) {
        when(events) {
            is NoteEvents.Order -> {
                if(events.noteOrderType::class == state.value.noteOrder::class && events.noteOrderType.orderType == state.value.noteOrder.orderType) {
                    return
                }
                getNotes(events.noteOrderType)
            }

            is NoteEvents.Restore -> {
                viewModelScope.launch {
                    noteUseCases.insertNoteUseCase(recentlyDeleted?:return@launch)
                    recentlyDeleted = null
                }
            }

            is NoteEvents.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }

            is NoteEvents.Delete -> {
                viewModelScope.launch {
                    noteUseCases.deleteNoteUseCase(events.note)
                    recentlyDeleted = events.note
                }
            }
        }
    }

    private fun getNotes(noteOrderType: NoteOrderType) {
        getNotesJob?.cancel()

        getNotesJob = noteUseCases.getNotes(noteOrderType).onEach { notes->
            _state.value = state.value.copy(
                notes = notes,
                noteOrder = noteOrderType
            )
        }.launchIn(viewModelScope)
    }

    init {
        getNotes(NoteOrderType.Title(OrderType.Asc))
    }
}