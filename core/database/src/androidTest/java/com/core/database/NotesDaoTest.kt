package com.core.database

import androidx.test.core.app.ApplicationProvider
import com.core.database.dao.NotesDao
import com.core.database.dao.TagsDao
import com.core.database.di.BaseApplicationTest
import com.core.database.entity.NoteEntity
import com.core.database.entity.NoteWithTagEntity
import com.core.database.entity.TagEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class NotesDaoTest {

    private lateinit var notesDb: NotesDb
    private lateinit var notesDao: NotesDao
    private lateinit var tagsDao: TagsDao

    @Before
    fun setUp() {
        val appContext = ApplicationProvider.getApplicationContext<BaseApplicationTest>()
        notesDb = appContext.applicationComponentTest.notesDb()
        notesDao = notesDb.getNotesDao()
        tagsDao = notesDb.getTagsDao()
    }

    @After
    fun tearDown() {
        notesDb.close()
    }

    @Test
    fun insertNote_getAllNotes() = runTest {
        //Arrange
        val tag = TagEntity(id = 1, name = "Test", color = "#FFFFFF")
        tagsDao.insertTag(tag)
        val note1 = NoteEntity(
            id = 1,
            content = "Test note",
            timestamp = 1633024800000L,
            tagId = 1,
            done = false
        )
        val note2 = NoteEntity(
            id = 2,
            content = "Test note",
            timestamp = 1633024800000L,
            tagId = 1,
            done = false
        )
        notesDao.upsertNotes(listOf(note1, note2))

        //Act
        val result = notesDao.getAllNotes().first()

        //Assert
        assertEquals(result.size, 2)
        assertEquals(result[0], note1)
        assertEquals(result[1], note2)
    }

    @Test
    fun insertNote_getNoteById() = runTest {
        //Arrange
        val tag = TagEntity(id = 1, name = "Test", color = "#FFFFFF")
        tagsDao.insertTag(tag)
        val note1 = NoteEntity(
            id = 1,
            content = "Test note",
            timestamp = 1633024800000L,
            tagId = 1,
            done = false
        )
        val note2 = NoteEntity(
            id = 2,
            content = "Test note",
            timestamp = 1633024800000L,
            tagId = 1,
            done = false
        )
        notesDao.upsertNotes(listOf(note1, note2))

        //Act
        val result = notesDao.getNoteById(1L)

        //Assert
        assertNotNull(result)
        assertEquals(result, note1)
    }

    @Test
    fun insertNote_getNoteWithTagById() = runTest {
        //Arrange
        val tag = TagEntity(id = 1, name = "Test", color = "#FFFFFF")
        tagsDao.insertTag(tag)
        val note1 = NoteEntity(
            id = 1,
            content = "Test note",
            timestamp = 1633024800000L,
            tagId = 1,
            done = false
        )
        val note2 = NoteEntity(
            id = 2,
            content = "Test note",
            timestamp = 1633024800000L,
            tagId = 1,
            done = false
        )
        notesDao.upsertNotes(listOf(note1, note2))

        //Act
        val result = notesDao.getNoteWithTagById(1L)

        //Assert
        assertNotNull(result)
        assertEquals(result, NoteWithTagEntity(note1, tag))
    }

    @Test
    fun insertNote_getAllNotesWithTag() = runTest {
        //Arrange
        val tag = TagEntity(id = 1, name = "Test", color = "#FFFFFF")
        tagsDao.insertTag(tag)
        val note1 = NoteEntity(
            id = 1,
            content = "Test note",
            timestamp = 1633024800000L,
            tagId = 1,
            done = false
        )
        val note2 = NoteEntity(
            id = 2,
            content = "Test note",
            timestamp = 1633024800000L,
            tagId = 1,
            done = false
        )
        notesDao.upsertNotes(listOf(note1, note2))

        //Act
        val result = notesDao.getAllNotesWithTag().first()

        //Assert
        assertEquals(result.size, 2)
        assertEquals(result[0], NoteWithTagEntity(note1, tag))
        assertEquals(result[1], NoteWithTagEntity(note2, tag))
    }
}