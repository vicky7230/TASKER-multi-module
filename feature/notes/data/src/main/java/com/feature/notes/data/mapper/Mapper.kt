package com.feature.notes.data.mapper

import com.core.database.entity.NoteEntity
import com.core.database.entity.NoteWithTagEntity
import com.feature.notes.domain.model.Note
import com.feature.notes.domain.model.NoteWithTag

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        content = content,
        timestamp = timestamp,
        tagId = tagId,
        done = done
    )
}

fun NoteEntity.toDomain(): Note {
    return Note(
        id = id,
        content = content,
        timestamp = timestamp,
        tagId = tagId,
        done = done
    )
}

fun NoteWithTagEntity.toDomain(): NoteWithTag {
    return NoteWithTag(
        id = note.id,
        content = note.content,
        timestamp = note.timestamp,
        tagId = note.tagId,
        done = note.done,
        tagColor = tag.color,
        tagName = tag.name
    )
}

fun NoteWithTag.toEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        content = content,
        timestamp = timestamp,
        tagId = tagId,
        done = done
    )
}