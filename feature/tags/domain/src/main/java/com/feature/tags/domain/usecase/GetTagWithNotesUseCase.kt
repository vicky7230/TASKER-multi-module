package com.feature.tags.domain.usecase

import com.core.domain.repo.TagsRepository
import javax.inject.Inject

class GetTagWithNotesUseCase
    @Inject
    constructor(
        private val tagsRepository: TagsRepository,
    ) {
        operator fun invoke(tagId: Long) = tagsRepository.getTagWithNotes(tagId)
    }
