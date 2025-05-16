package com.ninidze.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.ninidze.domain.model.CreateNote
import com.ninidze.domain.model.Note

data class NoteDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String
) {
    fun toDomain() = Note(
        id = id,
        title = title,
        content = content
    )

    companion object {
        fun fromDomain(note: Note) = NoteDto(
            id = note.id,
            title = note.title,
            content = note.content
        )
    }
}

data class CreateNoteDto(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String
) {
    fun toDomain() = CreateNote(
        title = title,
        content = content
    )

    companion object {
        fun fromDomain(note: CreateNote) = CreateNoteDto(
            title = note.title,
            content = note.content
        )
    }
}

data class DeleteNoteDto(
    @SerializedName("success") val success: Boolean
)
