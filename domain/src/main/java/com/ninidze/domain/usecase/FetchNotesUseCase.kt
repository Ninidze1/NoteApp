package com.ninidze.domain.usecase

import com.ninidze.domain.repository.NotesRepository
import javax.inject.Inject

class FetchNotesUseCase @Inject constructor(private val repository: NotesRepository) {
    suspend operator fun invoke() = repository.fetchNotes()
}
