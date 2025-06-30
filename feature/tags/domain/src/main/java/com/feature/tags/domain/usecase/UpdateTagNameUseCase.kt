package com.feature.tags.domain.usecase

import com.core.domain.repo.TagsRepository
import javax.inject.Inject

class UpdateTagNameUseCase
    @Inject
    constructor(
        private val repository: TagsRepository,
    ) {
        suspend operator fun invoke(
            tagId: Long,
            newName: String,
        ) = repository.updateTagName(tagId, newName)
    }
