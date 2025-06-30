package com.core.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "tags")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val color: String,
)

data class TagWithNotesEntity(
    @Embedded val tag: TagEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "tagId",
    )
    val notes: List<NoteEntity>,
)

data class UpdateTagName(
    val id: Long,
    val name: String,
)
