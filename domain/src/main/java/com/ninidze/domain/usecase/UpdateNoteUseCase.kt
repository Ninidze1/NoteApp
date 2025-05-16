package com.ninidze.domain.usecase
import com.ninidze.domain.model.Note
import com.ninidze.domain.repository.NotesRepository
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(private val repository: NotesRepository) {
    suspend operator fun invoke(note: Note) = repository.updateNote(note)
}
