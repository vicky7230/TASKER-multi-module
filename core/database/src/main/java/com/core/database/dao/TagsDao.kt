package com.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.core.database.entity.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TagsDao {
    @Insert
    suspend fun insertTag(note: TagEntity): Long

    @Insert
    suspend fun insertTags(note: List<TagEntity>): List<Long>

    @Query("SELECT * from tags")
    fun getAllTags(): Flow<List<TagEntity>>
}