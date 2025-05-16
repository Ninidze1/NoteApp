package com.ninidze.framework.presentation.model

import com.ninidze.domain.model.Note

sealed interface NoteActions {
    data class Create(
        val title: String,
        val content: String
    ) : NoteActions
    data class Update(val note: Note) : NoteActions
    data class Delete(val id: String) : NoteActions
    data object ShowEmptyNoteNotification : NoteActions
}
