package com.feature.notes.data.mapper

import com.core.database.entity.NoteEntity
import com.feature.notes.domain.model.Note

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        content = content
    )
}

fun NoteEntity.toDomain(): Note {
    return Note(
        id = id,
        content = content
    )
}