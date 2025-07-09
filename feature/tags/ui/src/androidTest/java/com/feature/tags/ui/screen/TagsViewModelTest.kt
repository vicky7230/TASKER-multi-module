package com.feature.tags.ui.screen

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.core.domain.model.Note
import com.core.domain.model.TagWithNotes
import com.core.domain.usecase.UpdateNoteDoneUseCase
import com.feature.tags.domain.usecase.GetTagWithNotesUseCase
import com.feature.tags.domain.usecase.UpdateTagNameUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TagsViewModelTest {
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var getTagWithNotesUseCase: GetTagWithNotesUseCase
    private lateinit var updateTagNameUseCase: UpdateTagNameUseCase
    private lateinit var updateNoteDoneUseCase: UpdateNoteDoneUseCase
    private lateinit var tagsViewModel: TagsViewModel
    private val testDispatcher = StandardTestDispatcher()

    private val tagWithNotes =
        TagWithNotes(
            id = 1L,
            name = "Work",
            color = "#61DEA4",
            notes =
                persistentListOf(
                    Note(
                        id = 1L,
                        content = "Test note 1",
                        timestamp = 123456789,
                        tagId = 1L,
                        done = false,
                        date = "2025-06-25",
                        time = "00:00:00",
                    ),
                    Note(
                        id = 2L,
                        content = "Test note 2",
                        timestamp = 123456790,
                        tagId = 1L,
                        done = false,
                        date = "2025-06-25",
                        time = "00:00:00",
                    ),
                ),
        )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getTagWithNotesUseCase = mockk()
        updateTagNameUseCase = mockk()
        updateNoteDoneUseCase = mockk()
    }

    @Test
    fun should_Emit_Tag_With_Notes_When_GetTagWithNotesUseCase_Is_Called() =
        runTest {
            // Arrange
            every { getTagWithNotesUseCase(any()) } returns flowOf(tagWithNotes)
            savedStateHandle = SavedStateHandle(mapOf("tagId" to 1L))

            // Act
            tagsViewModel =
                TagsViewModel(
                    savedStateHandle = savedStateHandle,
                    getTagWithNotesUseCase = getTagWithNotesUseCase,
                    updateTagNameUseCase = updateTagNameUseCase,
                    updateNoteDoneUseCase = updateNoteDoneUseCase,
                )

            // Assert
            tagsViewModel.tagsUiState.test {
                assertEquals(TagsUiState.Idle, awaitItem())
                assertEquals(TagsUiState.Loading, awaitItem())
                assertEquals(TagsUiState.TagLoaded(tagWithNotes), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun should_Emit_Error_When_GetTagWithNotesUseCase_Throws_Exception() =
        runTest {
            // Arrange
            every { getTagWithNotesUseCase(any()) } returns flow { throw RuntimeException("Error") }
            savedStateHandle = SavedStateHandle(mapOf("tagId" to 1L))

            // Act
            tagsViewModel =
                TagsViewModel(
                    savedStateHandle = savedStateHandle,
                    getTagWithNotesUseCase = getTagWithNotesUseCase,
                    updateTagNameUseCase = updateTagNameUseCase,
                    updateNoteDoneUseCase = updateNoteDoneUseCase,
                )

            // Assert
            tagsViewModel.tagsUiState.test {
                assertEquals(TagsUiState.Idle, awaitItem())
                assertEquals(TagsUiState.Loading, awaitItem())
                assertEquals(TagsUiState.Error("Error fetching tags"), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun updateTagName_should_update_tag_name_and_emit_TagLoaded_state() =
        runTest {
            // Arrange
            every { getTagWithNotesUseCase(any()) } returns flowOf(tagWithNotes)
            coEvery { updateTagNameUseCase(any(), any()) } returns 1
            savedStateHandle = SavedStateHandle(mapOf("tagId" to 1L))
            tagsViewModel =
                TagsViewModel(
                    savedStateHandle = savedStateHandle,
                    getTagWithNotesUseCase = getTagWithNotesUseCase,
                    updateTagNameUseCase = updateTagNameUseCase,
                    updateNoteDoneUseCase = updateNoteDoneUseCase,
                )

            // Act & Assert
            tagsViewModel.tagsUiState.test {
                assertEquals(TagsUiState.Idle, awaitItem())
                assertEquals(TagsUiState.Loading, awaitItem())
                assertEquals(TagsUiState.TagLoaded(tagWithNotes), awaitItem())
                tagsViewModel.updateTagName(tagId = 1L, newName = "Updated Work")
                advanceUntilIdle()
                coVerify { updateTagNameUseCase(tagId = 1L, newName = "Updated Work") }
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun showRenameTagBottomSheet_should_emit_ShowRenameTagBottomSheet_state() =
        runTest {
            // Arrange
            every { getTagWithNotesUseCase(any()) } returns flowOf(tagWithNotes)
            savedStateHandle = SavedStateHandle(mapOf("tagId" to 1L))

            // Act
            tagsViewModel =
                TagsViewModel(
                    savedStateHandle = savedStateHandle,
                    getTagWithNotesUseCase = getTagWithNotesUseCase,
                    updateTagNameUseCase = updateTagNameUseCase,
                    updateNoteDoneUseCase = updateNoteDoneUseCase,
                )

            // Assert
            tagsViewModel.tagsUiState.test {
                assertEquals(TagsUiState.Idle, awaitItem())
                assertEquals(TagsUiState.Loading, awaitItem())
                assertEquals(TagsUiState.TagLoaded(tagWithNotes), awaitItem())
                tagsViewModel.showRenameTagBottomSheet(
                    TagsUiBottomSheet.RenameTagBottomSheet(
                        tagId = 1L,
                        tagName = "Work",
                        tagColor = "#FFFFFF",
                    ),
                )
                val result = awaitItem()
                assertTrue(result is TagsUiState.TagLoaded)
                assertTrue((result as TagsUiState.TagLoaded).tagsUiBottomSheet is TagsUiBottomSheet.RenameTagBottomSheet)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
