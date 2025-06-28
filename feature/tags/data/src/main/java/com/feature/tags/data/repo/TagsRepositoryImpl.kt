package com.feature.tags.data.repo

import com.core.database.NotesDb
import com.core.database.entity.TagEntity
import com.core.database.entity.TagWithNotesEntity
import com.core.domain.model.Tag
import com.core.domain.model.TagWithNotes
import com.core.domain.repo.TagsRepository
import com.feature.tags.data.mapper.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TagsRepositoryImpl
    @Inject
    constructor(
        private val notesDb: NotesDb,
    ) : TagsRepository {
        override fun getTagWithNotes(tagId: Long): Flow<TagWithNotes> =
            notesDb
                .getTagsDao()
                .getTagWithNotes(tagId)
                .map { it.toDomain() }

        override fun getAllTagsWithNotes(): Flow<List<TagWithNotes>> =
            notesDb
                .getTagsDao()
                .getAllTagsWithNotes()
                .map { it.map { tagWithNotesEntity: TagWithNotesEntity -> tagWithNotesEntity.toDomain() } }

        override fun getAllTags(): Flow<List<Tag>> =
            notesDb
                .getTagsDao()
                .getAllTags()
                .map { it.map { tagEntity: TagEntity -> tagEntity.toDomain() } }
    }
