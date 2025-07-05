package com.core.domain.repo

import com.core.domain.model.Tag
import com.core.domain.model.TagWithNotes
import kotlinx.coroutines.flow.Flow

interface TagsRepository {
    fun getTagWithNotes(tagId: Long): Flow<TagWithNotes>

    fun getAllTagsWithNotes(): Flow<List<TagWithNotes>>

    fun getAllTags(): Flow<List<Tag>>

    suspend fun updateTagName(
        tagId: Long,
        newName: String,
    ): Int

    suspend fun insertTag(tag: Tag): Long
}
