package com.feature.notes.domain.usecase

import com.feature.notes.domain.repo.NotesRepository
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    operator fun invoke() = repository.getAllNotes()
}