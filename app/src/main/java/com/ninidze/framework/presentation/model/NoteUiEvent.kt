package com.ninidze.framework.presentation.model

sealed class NoteUiEvent {
        data class ShowSnackBar(val message: String) : NoteUiEvent()
    }