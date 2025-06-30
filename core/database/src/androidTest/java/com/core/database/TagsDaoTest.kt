package com.core.database

import androidx.test.core.app.ApplicationProvider
import com.core.database.dao.NotesDao
import com.core.database.dao.TagsDao
import com.core.database.di.BaseApplicationTest
import com.core.database.entity.NoteEntity
import com.core.database.entity.TagEntity
import com.core.database.entity.TagWithNotesEntity
import com.core.database.entity.UpdateTagName
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class TagsDaoTest {
    private lateinit var notesDb: NotesDb
    private lateinit var tagsDao: TagsDao
    private lateinit var notesDao: NotesDao

    @Before
    fun setUp() {
        val appContext = ApplicationProvider.getApplicationContext<BaseApplicationTest>()
        notesDb = appContext.applicationComponentTest.notesDb()
        tagsDao = notesDb.getTagsDao()
        notesDao = notesDb.getNotesDao()
    }

    @After
    fun tearDown() {
        notesDb.close()
    }

    @Test
    fun insertTags_getAllTags() =
        runTest {
            // Arrange
            val tags =
                listOf(
                    TagEntity(1, "tag1", "#ffffff"),
                    TagEntity(2, "tag2", "#000000"),
                    TagEntity(3, "tag3", "#ff0000"),
                )
            tagsDao.insertTags(tags)

            // Act
            val result = tagsDao.getAllTags().first()

            // Assert
            assertEquals(3, result.size)
            assertEquals(tags[0], result[0])
            assertEquals(tags[1], result[1])
            assertEquals(tags[2], result[2])
        }

    @Test
    fun insertTag_getAllTags() =
        runTest {
            // Arrange
            val tag = TagEntity(1, "tag1", "#ffffff")
            tagsDao.insertTag(tag)

            // Act
            val result = tagsDao.getAllTags().first()

            // Assert
            assertEquals(1, result.size)
            assertEquals(tag, result[0])
        }

    @Test
    fun insertTags_getAllTagsWithNotes() =
        runTest {
            // Arrange
            val tags =
                listOf(
                    TagEntity(1, "tag1", "#ffffff"),
                    TagEntity(2, "tag2", "#000000"),
                )
            tagsDao.insertTags(tags)
            val note1 =
                NoteEntity(
                    id = 1,
                    content = "Test note",
                    timestamp = 1633024800000L,
                    tagId = 1,
                    done = false,
                    date = "2025-06-25",
                    time = "00:00:00",
                )
            val note2 =
                NoteEntity(
                    id = 2,
                    content = "Test note",
                    timestamp = 1633024800000L,
                    tagId = 2,
                    done = false,
                    date = "2025-06-25",
                    time = "00:00:00",
                )
            notesDao.upsertNotes(listOf(note1, note2))
            // Act
            val result = tagsDao.getAllTagsWithNotes().first()

            // Assert
            assertEquals(result.size, 2)
            assertEquals(TagWithNotesEntity(tag = tags[0], notes = listOf(note1)), result[0])
            assertEquals(TagWithNotesEntity(tag = tags[1], notes = listOf(note2)), result[1])
        }

    @Test
    fun insertTag_getTagWithNotes() =
        runTest {
            // Arrange
            val tag = TagEntity(1, "tag1", "#ffffff")
            tagsDao.insertTag(tag)
            val note =
                NoteEntity(
                    id = 1,
                    content = "Test note",
                    timestamp = 1633024800000L,
                    tagId = 1,
                    done = false,
                    time = "00:00:00",
                    date = "2025-06-25",
                )
            notesDao.upsertNotes(listOf(note))

            // Act
            val result = tagsDao.getTagWithNotes(1).first()

            // Assert
            assertEquals(TagWithNotesEntity(tag = tag, notes = listOf(note)), result)
        }

    @Test
    fun updateTagName_getUpdatedTag() =
        runTest {
            // Arrange
            val tag = TagEntity(1, "tag1", "#ffffff")
            tagsDao.insertTag(tag)

            // Act
            tagsDao.updateTagName(UpdateTagName(1, "tag2"))
            val result = tagsDao.getAllTags().first()

            // Assert
            assertEquals(1, result.size)
            assertEquals("tag2", result[0].name)
        }
}
