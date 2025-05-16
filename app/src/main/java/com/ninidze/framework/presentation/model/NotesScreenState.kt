package com.ninidze.framework.presentation.model

import com.ninidze.domain.model.Note

sealed interface NotesScreenState {
    data object Loading : NotesScreenState
    data object Empty : NotesScreenState
    data class Data(val notes: List<Note>) : NotesScreenState
}
