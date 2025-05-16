package com.ninidze.data.remote

import com.ninidze.data.dto.CreateNoteDto
import com.ninidze.data.dto.NoteDto
import com.ninidze.domain.model.CreateNote
import com.ninidze.domain.model.Note
import javax.inject.Inject

class NotesRemoteDataSource @Inject constructor(private val api: NotesApi) {

    suspend fun getNotes() = api.getNotes().map { it.toDomain() }

    suspend fun createNote(createNote: CreateNote) =
        api.createNote(request = CreateNoteDto.fromDomain(createNote))

    suspend fun updateNote(id: String, note: Note) =
        api.updateNote(id = id, request = NoteDto.fromDomain(note)).toDomain()

    suspend fun deleteNote(id: String) = api.deleteNote(id = id).success
}
