package com.feature.tags.data.mapper

import com.core.database.entity.NoteEntity
import com.core.database.entity.TagEntity
import com.core.database.entity.TagWithNotesEntity
import com.core.domain.model.Note
import com.core.domain.model.Tag
import com.core.domain.model.TagWithNotes
import kotlinx.collections.immutable.toPersistentList

fun NoteEntity.toDomain(): Note =
    Note(
        id = id,
        content = content,
        timestamp = timestamp,
        tagId = tagId,
        done = done,
        date = date,
        time = time,
    )

fun TagWithNotesEntity.toDomain(): TagWithNotes =
    TagWithNotes(
        id = tag.id,
        name = tag.name,
        color = tag.color,
        notes = notes.map { it.toDomain() }.toPersistentList(),
    )

fun TagEntity.toDomain(): Tag =
    Tag(
        id = id,
        name = name,
        color = color,
    )
