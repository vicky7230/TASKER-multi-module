package com.feature.notes.domain.usecase

import com.feature.notes.domain.repo.NotesRepository
import javax.inject.Inject

class GetAllTagsWithNotesUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    operator fun invoke() = repository.getAllTagsWithNotes()
}