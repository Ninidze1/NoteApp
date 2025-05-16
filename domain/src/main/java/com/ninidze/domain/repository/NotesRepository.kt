package com.ninidze.domain.repository

import com.ninidze.domain.model.CreateNote
import com.ninidze.domain.model.Note

interface NotesRepository {
    suspend fun fetchNotes(): List<Note>
    suspend fun createNote(createNote: CreateNote): Note
    suspend fun updateNote(note: Note): Note
    suspend fun deleteNote(id: String): Boolean
}
