package com.feature.notes.data.repo

import com.core.database.NotesDb
import com.core.database.dao.NotesDao
import com.core.database.dao.TagsDao
import com.feature.notes.data.mapper.toEntity
import com.feature.notes.domain.model.NoteWithTag
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class NotesRepositoryImplTest {
    private val notesDao: NotesDao = mockk(relaxed = true)
    private val tagsDao: TagsDao = mockk(relaxed = true)
    private val notesDb: NotesDb =
        mockk {
            every { getNotesDao() } returns notesDao
            every { getTagsDao() } returns tagsDao
        }

    private lateinit var repository: NotesRepositoryImpl

    @Before
    fun setUp() {
        repository = NotesRepositoryImpl(notesDb)
    }

    @After
    fun tearDown() {
        // nothing
    }

    @Test
    fun `upsertNotes should map and return IDs`() =
        runTest {
            val notes =
                listOf(
                    NoteWithTag(
                        1,
                        "Test",
                        timestamp = 1233L,
                        tagId = 1,
                        tagName = "Tag",
                        tagColor = "#FF0000",
                        done = false,
                    ),
                )
            val expectedIds = listOf(100L)

            coEvery { notesDao.upsertNotes(any()) } returns expectedIds

            val result = repository.upsertNotes(notes)

            assertEquals(expectedIds, result)
            coVerify { notesDao.upsertNotes(notes.map { it.toEntity() }) }
        }
}
