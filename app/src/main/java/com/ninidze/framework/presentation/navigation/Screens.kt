package com.ninidze.framework.presentation.navigation

import com.ninidze.domain.model.Note
import kotlinx.serialization.Serializable

@Serializable
object NotesScreen

@Serializable
data class NoteDetailScreen(
    val id: String,
    val title: String,
    val content: String
) {
    fun toNote() = Note(id, title, content)

    companion object {
        fun fromNote(note: Note) = NoteDetailScreen(
            id = note.id,
            title = note.title,
            content = note.content
        )
        val Empty = NoteDetailScreen("", "", "")
    }
}