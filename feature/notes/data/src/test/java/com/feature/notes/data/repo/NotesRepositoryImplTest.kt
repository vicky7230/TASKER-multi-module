package com.feature.notes.data.repo

import app.cash.turbine.test
import com.core.database.NotesDb
import com.core.database.dao.NotesDao
import com.core.database.dao.TagsDao
import com.core.database.entity.NoteEntity
import com.core.database.entity.NoteWithTagEntity
import com.core.database.entity.TagEntity
import com.core.domain.model.NoteWithTag
import com.core.domain.repo.NotesRepository
import com.feature.notes.data.mapper.toDomain
import com.feature.notes.data.mapper.toEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
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

    private lateinit var notesRepository: NotesRepository

    @Before
    fun setUp() {
        notesRepository = NotesRepositoryImpl(notesDb)
    }

    @After
    fun tearDown() {
        // nothing
    }

    @Test
    fun `upsertNotes should map and return IDs`() =
        runTest {
            // Arrange
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
                        date = "2025-06-25",
                        time = "00:00:00",
                    ),
                )
            val expectedIds = listOf(1L)

            coEvery { notesDao.upsertNotes(any()) } returns expectedIds

            // Act
            val result = notesRepository.upsertNotes(notes)

            // Assert
            assertEquals(expectedIds, result)
            coVerify { notesDao.upsertNotes(notes.map { it.toEntity() }) }
        }

    @Test
    fun `getAllNotes should return all notes`() =
        runTest {
            // Arrange
            val notes =
                listOf(
                    NoteEntity(
                        1,
                        "Test 1",
                        1233L,
                        1,
                        false,
                        date = "2025-06-25",
                        time = "00:00:00",
                    ),
                    NoteEntity(
                        2,
                        "Test 2",
                        1233L,
                        1,
                        false,
                        date = "2025-06-25",
                        time = "00:00:00",
                    ),
                )

            val expectedResult = notes.map { it.toDomain() }

            coEvery { notesDao.getAllNotes() } returns flowOf(notes)

            // Act & Assert
            notesRepository.getAllNotes().test {
                val result = awaitItem()
                assertEquals(expectedResult, result)
                awaitComplete()
            }
        }

    @Test
    fun `getAllNotesWithTag should return all notes with Tag`() =
        runTest {
            // Arrange
            val notes =
                listOf(
                    NoteWithTagEntity(
                        note =
                            NoteEntity(
                                1,
                                "Test 1",
                                1233L,
                                1,
                                false,
                                date = "2025-06-25",
                                time = "00:00:00",
                            ),
                        tag = TagEntity(1, "TestTag", "#FF0000"),
                    ),
                )
            val expectedResult = notes.map { it.toDomain() }
            coEvery { notesDao.getAllNotesWithTag() } returns flowOf(notes)

            notesRepository.getAllNotesWithTag().test {
                val result = awaitItem()
                assertEquals(expectedResult, result)
                awaitComplete()
            }
        }

    @Test
    fun `getNoteById should return note by ID`() =
        runTest {
            // Arrange
            val noteId = 1L
            val note =
                NoteEntity(
                    noteId,
                    "Test 1",
                    1233L,
                    1,
                    false,
                    date = "2025-06-25",
                    time = "00:00:00",
                )
            val expectedResult = note.toDomain()
            coEvery { notesDao.getNoteById(noteId) } returns note

            // Act & Assert
            val result = notesRepository.getNoteById(noteId)
            assertEquals(expectedResult, result)
        }

    @Test
    fun `getNoteWithTagById should return note with Tag by ID`() =
        runTest {
            // Arrange
            val noteId = 1L
            val note =
                NoteWithTagEntity(
                    note =
                        NoteEntity(
                            noteId,
                            "Test 1",
                            1233L,
                            1,
                            false,
                            date = "2025-06-25",
                            time = "00:00:00",
                        ),
                    tag = TagEntity(1, "TestTag", "#FF0000"),
                )
            val expectedResult = note.toDomain()
            coEvery { notesDao.getNoteWithTagById(noteId) } returns note
            // Act & Assert
            val result = notesRepository.getNoteWithTagById(noteId)
            assertEquals(expectedResult, result)
        }

    /*@Test
    fun `getAllTagsWithNotes should return all tags with notes`() =
        runTest {
            // Arrange
            val tagsWithNotes =
                listOf(
                    TagWithNotesEntity(
                        tag = TagEntity(1, "TestTag1", "#FF0000"),
                        notes =
                            listOf(
                                NoteEntity(1, "Test 1", 1233L, 1, false, date = "2025-06-25", time = "00:00:00"),
                                NoteEntity(2, "Test 2", 1234L, 1, false, date = "2025-06-25", time = "00:00:00"),
                            ),
                    ),
                )
            coEvery { tagsDao.getAllTagsWithNotes() } returns flowOf(tagsWithNotes)
            // Act & Assert
            notesRepository.getAllTagsWithNotes().test {
                val result = awaitItem()
                assertEquals(tagsWithNotes.map { it.toDomain() }, result)
                awaitComplete()
            }
        }*/
}
