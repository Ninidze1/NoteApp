package com.ninidze.framework.presentation.model

import com.ninidze.domain.model.Note

data class NoteUiState(
    val notes: List<Note> = emptyList(),
    val loading: Boolean = false
)
