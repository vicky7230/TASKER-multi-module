package com.feature.notes.data.mapper

import com.core.database.entity.NoteEntity
import com.core.database.entity.NoteWithTagEntity
import com.core.database.entity.TagWithNotesEntity
import com.feature.notes.domain.model.Note
import com.feature.notes.domain.model.NoteWithTag
import com.feature.notes.domain.model.TagWithNotes

fun Note.toEntity(): NoteEntity =
    NoteEntity(
        id = id,
        content = content,
        timestamp = timestamp,
        tagId = tagId,
        done = done,
    )

fun NoteEntity.toDomain(): Note =
    Note(
        id = id,
        content = content,
        timestamp = timestamp,
        tagId = tagId,
        done = done,
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
    )

fun NoteWithTag.toEntity(): NoteEntity =
    NoteEntity(
        id = id,
        content = content,
        timestamp = timestamp,
        tagId = tagId,
        done = done,
    )

fun TagWithNotesEntity.toDomain(): TagWithNotes =
    TagWithNotes(
        id = tag.id,
        name = tag.name,
        color = tag.color,
        notes = notes.map { it.toDomain() },
    )
