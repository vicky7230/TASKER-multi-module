package com.feature.notes.domain.usecase

import com.feature.notes.domain.model.Note
import com.feature.notes.domain.repo.NotesRepository
import javax.inject.Inject

class InsertNoteUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.insertNote(note)
    }
}