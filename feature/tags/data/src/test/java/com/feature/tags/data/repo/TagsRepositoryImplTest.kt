package com.feature.tags.data.repo

import app.cash.turbine.test
import com.core.database.NotesDb
import com.core.database.dao.TagsDao
import com.core.database.entity.NoteEntity
import com.core.database.entity.TagEntity
import com.core.database.entity.TagWithNotesEntity
import com.core.database.entity.UpdateTagName
import com.core.domain.model.Tag
import com.core.domain.repo.TagsRepository
import com.feature.tags.data.mapper.toDomain
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

class TagsRepositoryImplTest {
    private val tagsDao: TagsDao = mockk()
    private val notesDb: NotesDb =
        mockk {
            every { getTagsDao() } returns tagsDao
        }

    private lateinit var tagsRepository: TagsRepository

    @Before
    fun setUp() {
        tagsRepository = TagsRepositoryImpl(notesDb)
    }

    @After
    fun tearDown() {
        // Nothing
    }

    val tagWithNotesEntity =
        TagWithNotesEntity(
            tag = TagEntity(1, "TestTag1", "#FF0000"),
            notes =
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
                        1234L,
                        1,
                        false,
                        date = "2025-06-25",
                        time = "00:00:00",
                    ),
                ),
        )

    val tagWithNotesEntityList =
        listOf(
            TagWithNotesEntity(
                tag = TagEntity(1, "TestTag1", "#FF0000"),
                notes =
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
                            1234L,
                            1,
                            false,
                            date = "2025-06-25",
                            time = "00:00:00",
                        ),
                    ),
            ),
            TagWithNotesEntity(
                tag = TagEntity(2, "TestTag2", "#006CFF"),
                notes =
                    listOf(
                        NoteEntity(
                            3,
                            "Test 3",
                            1233L,
                            2,
                            false,
                            date = "2025-06-25",
                            time = "00:00:00",
                        ),
                        NoteEntity(
                            4,
                            "Test 4",
                            1234L,
                            2,
                            false,
                            date = "2025-06-25",
                            time = "00:00:00",
                        ),
                    ),
            ),
        )

    val tagsList =
        listOf(
            TagEntity(
                id = 1,
                name = "Work",
                color = "#FFFFFF",
            ),
            TagEntity(
                id = 2,
                name = "Family",
                color = "#000000",
            ),
        )

    @Test
    fun `getTagWithNotes should return tag with all notes`() =
        runTest {
            // Arrange
            every { tagsDao.getTagWithNotes(1L) } returns flowOf(tagWithNotesEntity)
            // Act & Assert
            tagsRepository.getTagWithNotes(tagId = 1).test {
                val result = awaitItem()
                assertEquals(tagWithNotesEntity.toDomain(), result)
                awaitComplete()
            }
        }

    @Test
    fun `getAllTagsWithNotes should return all tags with notes`() =
        runTest {
            // Arrange
            every { tagsDao.getAllTagsWithNotes() } returns flowOf(tagWithNotesEntityList)
            // Act & Assert
            tagsRepository.getAllTagsWithNotes().test {
                val result = awaitItem()
                assertEquals(tagWithNotesEntityList.map { it.toDomain() }, result)
                awaitComplete()
            }
        }

    @Test
    fun `getAllTags should return all tags`() =
        runTest {
            // Arrange
            every { tagsDao.getAllTags() } returns flowOf(tagsList)
            // Act & Assert
            tagsRepository.getAllTags().test {
                val result = awaitItem()
                assertEquals(tagsList.map { it.toDomain() }, result)
                awaitComplete()
            }
        }

    @Test
    fun `updateTagName should update tag name`() =
        runTest {
            // Arrange
            val tagId = 1L
            val newName = "Tag2"
            coEvery { tagsDao.updateTagName(any()) } returns 1
            // Act
            val result = tagsRepository.updateTagName(tagId = tagId, newName = newName)
            // Assert
            assertEquals(1, result)
            coVerify { tagsDao.updateTagName(UpdateTagName(tagId, newName)) }
        }

    @Test
    fun `insertTag should insert new tag`() =
        runTest {
            // Arrange
            val tagName = "Tag1"
            val tagId = 1L
            val tagColor = "#FFFFFF"
            coEvery { tagsDao.insertTag(any()) } returns tagId
            // Act
            val result = tagsRepository.insertTag(Tag(id = tagId, name = tagName, color = "#FFFFFF"))
            // Assert
            assertEquals(tagId, result)
            coVerify { tagsDao.insertTag(TagEntity(tagId, tagName, tagColor)) }
        }
}
