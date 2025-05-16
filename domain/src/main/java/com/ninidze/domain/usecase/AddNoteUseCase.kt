package com.ninidze.domain.usecase

import com.ninidze.domain.model.CreateNote
import com.ninidze.domain.repository.NotesRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(private val repository: NotesRepository) {
    suspend operator fun invoke(createNote: CreateNote) =
        repository.createNote(createNote)
}
