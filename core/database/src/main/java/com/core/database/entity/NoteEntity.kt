@file:SuppressLint("NewApi")

package com.core.database.entity

import android.annotation.SuppressLint
import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.core.database.entity.ActiveNoteEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
    val done: Boolean = false,
    @ColumnInfo(defaultValue = "false")
    val isDeleted: Boolean = false,
    @ColumnInfo(defaultValue = "'1970-01-01'")
    val date: String = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
    @ColumnInfo(defaultValue = "'00:00:00'")
    val time: String = LocalDate.now().format(DateTimeFormatter.ofPattern("HH:mm:00")),
)

fun NoteEntity.toActiveNoteEntity() =
    ActiveNoteEntity(
        id = id,
        content = content,
        timestamp = timestamp,
        tagId = tagId,
        done = done,
        isDeleted = isDeleted,
        date = date,
        time = time,
    )

@DatabaseView("SELECT * FROM notes WHERE isDeleted = 0")
data class ActiveNoteEntity(
    val id: Long,
    val content: String,
    val timestamp: Long,
    val tagId: Long,
    val done: Boolean,
    val isDeleted: Boolean,
    val date: String,
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

data class UpdateNoteDone(
    val id: Long,
    val done: Boolean,
)

data class UpdateNoteDeleted(
    val id: Long,
    val isDeleted: Boolean,
)
