package com.ninidze.framework.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ninidze.domain.model.CreateNote
import com.ninidze.domain.model.Note
import com.ninidze.domain.usecase.AddNoteUseCase
import com.ninidze.domain.usecase.DeleteNoteUseCase
import com.ninidze.domain.usecase.FetchNotesUseCase
import com.ninidze.domain.usecase.UpdateNoteUseCase
import com.ninidze.framework.presentation.model.NoteActions
import com.ninidze.framework.presentation.model.NoteUiEvent
import com.ninidze.framework.presentation.model.NoteUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val fetchNotesUseCase: FetchNotesUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
) : ViewModel() {

    private val _uiState = mutableStateOf(NoteUiState())
    val uiState: State<NoteUiState> = _uiState

    private val _eventFlow = MutableSharedFlow<NoteUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        initNotes()
    }

    fun onAction(action: NoteActions) {
        when (action) {
            is NoteActions.Create -> addNote(action.title, action.content)
            is NoteActions.Delete -> deleteNote(action.id)
            is NoteActions.Update -> updateNote(action.note)
            NoteActions.ShowEmptyNoteNotification -> viewModelScope.launch {
                _eventFlow.emit(NoteUiEvent.ShowSnackBar("Please fill in the note details."))
            }
        }
    }

    private fun initNotes() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(loading = true)
                val notes = fetchNotesUseCase()
                _uiState.value = _uiState.value.copy(
                    notes = notes,
                    loading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(loading = false)
                _eventFlow.emit(NoteUiEvent.ShowSnackBar("Error fetching notes: ${e.message}"))
            }
        }
    }

    private fun addNote(title: String, content: String) {
        viewModelScope.launch {
            try {
                val newNote = addNoteUseCase(CreateNote(title, content))
                _uiState.value = _uiState.value.copy(
                    notes = _uiState.value.notes + newNote
                )
            } catch (e: Exception) {
                _eventFlow.emit(NoteUiEvent.ShowSnackBar("Failed to add note: ${e.message}"))
            }
        }
    }

    private fun updateNote(note: Note) {
        viewModelScope.launch {
            try {
                val updatedNote = updateNoteUseCase(note)
                _uiState.value = _uiState.value.copy(
                    notes = _uiState.value.notes.map {
                        if (it.id == note.id) updatedNote else it
                    }
                )
            } catch (e: Exception) {
                _eventFlow.emit(NoteUiEvent.ShowSnackBar("Failed to update note: ${e.message}"))
            }
        }
    }

    private fun deleteNote(id: String) {
        viewModelScope.launch {
            try {
                deleteNoteUseCase(id)
                _uiState.value = _uiState.value.copy(
                    notes = _uiState.value.notes.filterNot { it.id == id }
                )
            } catch (e: Exception) {
                _eventFlow.emit(NoteUiEvent.ShowSnackBar("Failed to delete note: ${e.message}"))
            }
        }
    }
}