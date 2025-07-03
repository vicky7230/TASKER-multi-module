package com.feature.notes.domain.usecase

import com.core.domain.model.Tag
import com.core.domain.repo.TagsRepository
import javax.inject.Inject

class CreateTagUseCase
    @Inject
    constructor(
        private val repository: TagsRepository,
    ) {
        suspend operator fun invoke(
            tagName: String,
            tagColor: String,
        ): Long =
            repository.insertTag(
                Tag(
                    name = tagName,
                    color = tagColor,
                ),
            )
    }
