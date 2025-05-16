package com.ninidze.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ninidze.domain.model.Note

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey val id: String,
    val title: String,
    val content: String,
    val lastModified: Long
) {
    fun toDomain() = Note(
        id = id,
        title = title,
        content = content,
        lastModified = lastModified
    )

    companion object {
        fun fromDomain(note: Note) = NoteEntity(
            id = note.id,
            title = note.title,
            content = note.content,
            lastModified = note.lastModified
        )
    }
}
