package com.feature.notes.data.mapper

import com.core.database.entity.NoteEntity
import com.core.database.entity.NoteWithTagEntity
import com.core.domain.model.Note
import com.core.domain.model.NoteWithTag

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

fun NoteWithTagEntity.toDomain(): NoteWithTag =
    NoteWithTag(
        id = note.id,
        content = note.content,
        timestamp = note.timestamp,
        tagId = note.tagId,
        done = note.done,
        tagColor = tag.color,
        tagName = tag.name,
        date = note.date,
        time = note.time,
    )

fun NoteWithTag.toEntity(): NoteEntity =
    NoteEntity(
        id = id,
        content = content,
        timestamp = timestamp,
        tagId = tagId,
        done = done,
        date = date,
        time = time,
    )
