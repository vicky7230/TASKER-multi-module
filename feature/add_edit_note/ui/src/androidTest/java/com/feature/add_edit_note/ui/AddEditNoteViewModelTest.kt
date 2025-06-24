package com.feature.add_edit_note.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.core.domain.model.Note
import com.core.domain.model.NoteWithTag
import com.core.domain.model.TagWithNotes
import com.core.domain.usecase.GetAllTagsWithNotesUseCase
import com.feature.add_edit_note.domain.usecase.GetNoteWithTagByIdUseCase
import com.feature.add_edit_note.domain.usecase.UpsertNotesUseCase
import com.feature.add_edit_note.ui.ui.AddEditNoteSideEffect
import com.feature.add_edit_note.ui.ui.AddEditNoteUiState
import com.feature.add_edit_note.ui.ui.AddEditNoteViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddEditNoteViewModelTest {
    private lateinit var getNoteWithTagByIdUseCase: GetNoteWithTagByIdUseCase

    private lateinit var upsertNotesUseCase: UpsertNotesUseCase
    private lateinit var getAllTagsWithNotesUseCase: GetAllTagsWithNotesUseCase
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: AddEditNoteViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val testNote =
        NoteWithTag(
            id = 1,
            content = "Original content",
            timestamp = 123456789,
            tagId = 1,
            done = false,
            tagName = "Work",
            tagColor = "#61DEA4",
        )
    private val tags =
        listOf(
            TagWithNotes(
                id = 1,
                name = "Work",
                color = "#61DEA4",
                notes =
                    persistentListOf(
                        Note(id = 1, content = "Original content", timestamp = 123456789, tagId = 1, done = false),
                    ),
            ),
            TagWithNotes(
                id = 2,
                name = "Shopping",
                color = "#F45E6D",
                notes =
                    persistentListOf(
                        Note(id = 2, content = "Original content 2", timestamp = 123456789, tagId = 2, done = false),
                    ),
            ),
        )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getNoteWithTagByIdUseCase = mockk()
        getAllTagsWithNotesUseCase = mockk()
        upsertNotesUseCase = mockk(relaxed = true) // allows empty behavior
    }

    @Test
    fun should_emit_NoteAndTags_state_when_note_is_found_by_id() =
        runTest {
            // Arrange
            coEvery { getNoteWithTagByIdUseCase(any()) } returns testNote
            every { getAllTagsWithNotesUseCase() } returns flowOf(tags)
            savedStateHandle = SavedStateHandle(mapOf("noteId" to 1L))

            // Act
            viewModel =
                AddEditNoteViewModel(
                    savedStateHandle,
                    getNoteWithTagByIdUseCase,
                    upsertNotesUseCase,
                    getAllTagsWithNotesUseCase,
                )

            // Assert
            viewModel.addEditeNoteUiState.test {
                assertEquals(AddEditNoteUiState.Idle, awaitItem())
                val state = awaitItem()
                val noteState = state as AddEditNoteUiState.NoteAndTags
                assertEquals(testNote, noteState.note)
                assertEquals(tags, noteState.tags)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun should_emit_NoteAndTags_state_with_new_note_when_note_not_found() =
        runTest {
            // Arrange
            coEvery { getNoteWithTagByIdUseCase(any()) } returns (null)
            every { getAllTagsWithNotesUseCase() } returns flowOf(tags)
            savedStateHandle = SavedStateHandle(mapOf("noteId" to 3L))

            // Act
            viewModel =
                AddEditNoteViewModel(
                    savedStateHandle,
                    getNoteWithTagByIdUseCase,
                    upsertNotesUseCase,
                    getAllTagsWithNotesUseCase,
                )

            // Assert
            viewModel.addEditeNoteUiState.test {
                assertEquals(AddEditNoteUiState.Idle, awaitItem())
                val state = awaitItem()
                assertTrue(state is AddEditNoteUiState.NoteAndTags)
                val noteState = state as AddEditNoteUiState.NoteAndTags
                assertEquals("", noteState.note.content)
                assertEquals(1, noteState.note.tagId)
                assertEquals(tags, noteState.tags)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun should_emit_Error_state_when_exception_is_thrown() =
        runTest {
            // Arrange
            coEvery { getNoteWithTagByIdUseCase(any()) } throws RuntimeException("DB failure")
            every { getAllTagsWithNotesUseCase() } returns flowOf(tags)
            savedStateHandle = SavedStateHandle(mapOf("noteId" to 1L))

            // Act
            viewModel =
                AddEditNoteViewModel(
                    savedStateHandle,
                    getNoteWithTagByIdUseCase,
                    upsertNotesUseCase,
                    getAllTagsWithNotesUseCase,
                )

            // Assert
            viewModel.addEditeNoteUiState.test {
                assertEquals(AddEditNoteUiState.Idle, awaitItem())
                val errorState = awaitItem()
                assertTrue(errorState is AddEditNoteUiState.Error)
                assertEquals((errorState as AddEditNoteUiState.Error).message, "Failed to load note")
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun onNoteContentChanged_should_update_note_content() =
        runTest {
            // Arrange
            coEvery { getNoteWithTagByIdUseCase(1L) } returns testNote
            every { getAllTagsWithNotesUseCase() } returns flowOf(tags)
            savedStateHandle = SavedStateHandle(mapOf("noteId" to 1L))

            viewModel =
                AddEditNoteViewModel(
                    savedStateHandle,
                    getNoteWithTagByIdUseCase,
                    upsertNotesUseCase,
                    getAllTagsWithNotesUseCase,
                )

            advanceUntilIdle()
            // Act
            viewModel.onNoteChange(testNote.copy(content = "Updated content"))

            // Assert
            viewModel.addEditeNoteUiState.test {
                val updated = awaitItem() as AddEditNoteUiState.NoteAndTags
                assertEquals("Updated content", updated.note.content)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun saveNote_should_call_upsert_and_emit_side_effect() =
        runTest {
            // Given
            coEvery { getNoteWithTagByIdUseCase(1L) } returns testNote
            coEvery { upsertNotesUseCase(any()) } just Runs

            savedStateHandle = SavedStateHandle(mapOf("noteId" to 1L))
            viewModel =
                AddEditNoteViewModel(
                    savedStateHandle,
                    getNoteWithTagByIdUseCase,
                    upsertNotesUseCase,
                    getAllTagsWithNotesUseCase,
                )
            advanceUntilIdle()

            // Then
            viewModel.sideEffect.test {
                // When
                viewModel.saveNote()
                // advanceUntilIdle() // Let saveNote coroutine complete
                assertEquals(AddEditNoteSideEffect.Finish, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            coVerify { upsertNotesUseCase(listOf(testNote)) }
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
