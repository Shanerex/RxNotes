package com.example.rxnotes.feature_note.presentation.add_edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rxnotes.feature_note.domain.models.InvalidNoteException
import com.example.rxnotes.feature_note.domain.models.Note
import com.example.rxnotes.feature_note.domain.use_cases.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _noteTitle = mutableStateOf(AddNoteTextFieldState(
        "Title..."
    ))
    val noteTitleState: State<AddNoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(AddNoteTextFieldState(
        "Type something..."
    ))
    val noteContentState: State<AddNoteTextFieldState> = _noteContent

    private val _noteColour = mutableStateOf(Note.noteColors.random().toArgb())
    val noteColourState: State<Int> = _noteColour

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId->
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNoteByIdUseCase(noteId)?.also { note->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitleState.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = noteContentState.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _noteColour.value = note.color
                    }
                }
            }
        }
    }

     fun onEvent(events: AddNoteEvents) {
         when(events) {
             is AddNoteEvents.EnteredTitle -> {
                _noteTitle.value = noteTitleState.value.copy(
                    text = events.value,
                )
             }
             is AddNoteEvents.ChangeTitleFocus -> {
                 _noteTitle.value = noteTitleState.value.copy(
                     isHintVisible = !events.focus.isFocused && noteTitleState.value.text.isBlank()
                 )
             }
             is AddNoteEvents.EnteredContent -> {
                 _noteContent.value = noteContentState.value.copy(
                     text = events.value,
                 )
             }
             is AddNoteEvents.ChangeContentFocus -> {
                 _noteContent.value = noteContentState.value.copy(
                     isHintVisible = !events.focus.isFocused && noteContentState.value.text.isBlank()
                 )
             }
             is AddNoteEvents.ChangeColour -> {
                _noteColour.value = events.colour
             }
             is AddNoteEvents.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.insertNoteUseCase(
                            Note(
                                title = noteTitleState.value.text,
                                content = noteContentState.value.text,
                                color = noteColourState.value,
                                timeStamp = System.currentTimeMillis(),
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UIEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(UIEvent.ShowSnackbar(e.message?:"Invalid Note! Note must have a title and content"))
                    }
                }
             }
         }
     }

    sealed class UIEvent {
        data class ShowSnackbar(val msg: String): UIEvent()
        object SaveNote: UIEvent()
    }
}