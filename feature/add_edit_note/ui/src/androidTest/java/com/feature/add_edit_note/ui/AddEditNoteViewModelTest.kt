package com.feature.add_edit_note.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.feature.add_edit_note.domain.usecase.GetNoteWithTagByIdUseCase
import com.feature.add_edit_note.domain.usecase.UpsertNotesUseCase
import com.feature.add_edit_note.ui.ui.AddEditNoteSideEffect
import com.feature.add_edit_note.ui.ui.AddEditNoteUiState
import com.feature.add_edit_note.ui.ui.AddEditNoteViewModel
import com.feature.notes.domain.model.NoteWithTag
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddEditNoteViewModelTest {


    private lateinit var getNoteWithTagByIdUseCase: GetNoteWithTagByIdUseCase

    private lateinit var upsertNotesUseCase: UpsertNotesUseCase

    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: AddEditNoteViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val testNote = NoteWithTag(
        id = 1,
        content = "Original content",
        timestamp = 123456789,
        tagId = 1,
        done = false,
        tagName = "Work",
        tagColor = "#61DEA4"
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getNoteWithTagByIdUseCase = mockk()
        upsertNotesUseCase = mockk(relaxed = true) // allows empty behavior
    }

    @Test
    fun should_emit_NoteData_state_when_note_is_found_by_id() = runTest {
        // Arrange
        coEvery { getNoteWithTagByIdUseCase(1L) } returns testNote
        savedStateHandle = SavedStateHandle(mapOf("noteId" to 1L))

        // Act
        viewModel =
            AddEditNoteViewModel(savedStateHandle, getNoteWithTagByIdUseCase, upsertNotesUseCase)

        // Assert
        viewModel.addEditeNoteUiState.test {
            assertEquals(AddEditNoteUiState.Idle, awaitItem())
            val state = awaitItem()
            val noteState = state as AddEditNoteUiState.NoteData
            assertEquals(testNote, noteState.note)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun should_emit_NoteData_state_with_new_note_when_note_not_found() = runTest {
        // Arrange
        coEvery { getNoteWithTagByIdUseCase(2L) } returns (null)

        savedStateHandle = SavedStateHandle(mapOf("noteId" to 2L))

        // Act
        viewModel =
            AddEditNoteViewModel(savedStateHandle, getNoteWithTagByIdUseCase, upsertNotesUseCase)

        // Assert
        viewModel.addEditeNoteUiState.test {
            assertEquals(AddEditNoteUiState.Idle, awaitItem())
            val state = awaitItem()
            assertTrue(state is AddEditNoteUiState.NoteData)
            val note = (state as AddEditNoteUiState.NoteData).note
            assertEquals("", note.content)
            assertEquals(1, note.tagId)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun onNoteContentChanged_should_update_note_content() = runTest {
        // Arrange
        coEvery { getNoteWithTagByIdUseCase(1L) } returns testNote
        savedStateHandle = SavedStateHandle(mapOf("noteId" to 1L))

        viewModel =
            AddEditNoteViewModel(savedStateHandle, getNoteWithTagByIdUseCase, upsertNotesUseCase)

        advanceUntilIdle()
        //Act
        viewModel.onNoteContentChanged("Updated content")


        // Assert
        viewModel.addEditeNoteUiState.test {
            val updated = awaitItem() as AddEditNoteUiState.NoteData
            assertEquals("Updated content", updated.note.content)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun saveNote_should_call_upsert_and_emit_side_effect() = runTest {
        // Given
        coEvery { getNoteWithTagByIdUseCase(1L) } returns testNote
        coEvery { upsertNotesUseCase(any()) } just Runs

        savedStateHandle = SavedStateHandle(mapOf("noteId" to 1L))
        viewModel = AddEditNoteViewModel(savedStateHandle, getNoteWithTagByIdUseCase, upsertNotesUseCase)

        advanceUntilIdle()


        // Then
        viewModel.sideEffect.test {

            // When
            viewModel.saveNote()
            //advanceUntilIdle() // Let saveNote coroutine complete

            assertEquals(AddEditNoteSideEffect.finish, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        coVerify { upsertNotesUseCase(listOf(testNote)) }

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}