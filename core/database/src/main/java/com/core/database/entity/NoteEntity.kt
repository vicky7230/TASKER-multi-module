package com.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val content: String
)