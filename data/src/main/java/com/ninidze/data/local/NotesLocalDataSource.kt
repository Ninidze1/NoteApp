package com.ninidze.data.local

import com.ninidze.domain.model.Note
import javax.inject.Inject

private fun Note.withTimestamp() =
    copy(lastModified = System.currentTimeMillis())

class NotesLocalDataSource @Inject constructor(private val dao: NotesDao) {

    suspend fun all() = dao.all().map { it.toDomain() }

    suspend fun upsertAll(notes: List<Note>) =
        dao.upsertAll(
            notes
                .map { it.withTimestamp() }
                .map(NoteEntity::fromDomain)
        )

    suspend fun upsert(note: Note) =
        dao.upsert(
            NoteEntity.fromDomain(note.withTimestamp())
        )

    suspend fun delete(id: String) = dao.delete(id)
}
