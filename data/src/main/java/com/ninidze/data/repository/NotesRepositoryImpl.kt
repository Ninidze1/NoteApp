package com.ninidze.data.repository

import com.ninidze.data.local.NotesLocalDataSource
import com.ninidze.data.remote.NotesRemoteDataSource
import com.ninidze.domain.model.CreateNote
import com.ninidze.domain.model.Note
import com.ninidze.domain.repository.NotesRepository
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val remote: NotesRemoteDataSource,
    private val local: NotesLocalDataSource
) : NotesRepository {

    override suspend fun fetchNotes(): List<Note> =
        local.all().ifEmpty {
            remote.getNotes().also { local.upsertAll(it) }
        }

    override suspend fun createNote(createNote: CreateNote): Note {
        val created = remote
            .createNote(createNote)
            .toDomain()
        local.upsert(created)
        return created
    }

    override suspend fun updateNote(note: Note): Note {
        val updated = remote.updateNote(id = note.id, note = note)
        local.upsert(updated)
        return updated
    }

    override suspend fun deleteNote(id: String): Boolean =
        remote.deleteNote(id = id).also { if (it) local.delete(id) }
}
