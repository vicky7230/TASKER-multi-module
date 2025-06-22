package com.feature.notes.domain.usecase

import com.core.domain.repo.NotesRepository
import javax.inject.Inject

class GetAllNotesWithTagUseCase
    @Inject
    constructor(
        private val repository: NotesRepository,
    ) {
        operator fun invoke() = repository.getAllNotesWithTag()
    }
