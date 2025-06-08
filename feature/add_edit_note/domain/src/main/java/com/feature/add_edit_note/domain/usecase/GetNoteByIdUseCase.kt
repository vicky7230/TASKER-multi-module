package com.feature.add_edit_note.domain.usecase

import com.feature.notes.domain.model.Note
import com.feature.notes.domain.repo.NotesRepository
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(id: Long): Note? {
        return repository.getNoteById(id)
    }
}
