package com.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    tableName = "notes",
    foreignKeys = [
        ForeignKey(
            entity = TagEntity::class,
            parentColumns = ["id"],
            childColumns = ["tagId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["tagId"])],
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val content: String,
    @ColumnInfo(defaultValue = "0")
    val timestamp: Long = System.currentTimeMillis(), // createdAt timestamp
    @ColumnInfo(defaultValue = "1")
    val tagId: Long,
    @ColumnInfo(defaultValue = "false")
    val done: Boolean,
    @ColumnInfo(defaultValue = "false")
    val isDeleted: Boolean = false,
    @ColumnInfo(defaultValue = "'1970-01-01'")
    val date: String,
    @ColumnInfo(defaultValue = "'00:00:00'")
    val time: String,
)

data class NoteWithTagEntity(
    @Embedded val note: NoteEntity,
    @Relation(
        parentColumn = "tagId",
        entityColumn = "id",
    )
    val tag: TagEntity,
)
