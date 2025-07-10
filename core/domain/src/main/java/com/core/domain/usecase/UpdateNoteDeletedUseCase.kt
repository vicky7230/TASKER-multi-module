package com.core.domain.usecase

import com.core.domain.repo.NotesRepository
import javax.inject.Inject

class UpdateNoteDeletedUseCase
    @Inject
    constructor(
        private val repository: NotesRepository,
    ) {
        suspend operator fun invoke(
            id: Long,
            deleted: Boolean,
        ) = repository.updateNoteDeleted(id, deleted)
    }
