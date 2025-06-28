package com.core.domain.usecase

import com.core.domain.repo.TagsRepository
import javax.inject.Inject

class GetAllTagsWithNotesUseCase
    @Inject
    constructor(
        private val tagsRepository: TagsRepository,
    ) {
        operator fun invoke() = tagsRepository.getAllTagsWithNotes()
    }
