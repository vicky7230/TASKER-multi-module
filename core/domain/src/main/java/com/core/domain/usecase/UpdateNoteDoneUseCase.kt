package com.core.domain.usecase

import com.core.domain.repo.NotesRepository
import javax.inject.Inject

class UpdateNoteDoneUseCase
    @Inject
    constructor(
        private val repository: NotesRepository,
    ) {
        suspend operator fun invoke(
            id: Long,
            done: Boolean,
        ) = repository.updateNoteDone(id, done)
    }
