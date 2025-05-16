package com.ninidze.domain.usecase

import com.ninidze.domain.repository.NotesRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(private val repository: NotesRepository) {
    suspend operator fun invoke(id: String) = repository.deleteNote(id)
}
