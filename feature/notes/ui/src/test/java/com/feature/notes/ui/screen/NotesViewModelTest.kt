package com.feature.notes.ui.screen

import android.util.Log
import app.cash.turbine.test
import com.feature.notes.domain.model.Note
import com.feature.notes.domain.model.NoteWithTag
import com.feature.notes.domain.model.TagWithNotes
import com.feature.notes.domain.usecase.GetAllNotesWithTagUseCase
import com.feature.notes.domain.usecase.GetAllTagsWithNotesUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NotesViewModelTest {
    private lateinit var getAllNotesWithTagUseCase: GetAllNotesWithTagUseCase
    private lateinit var getAllTagsWithNotesUseCase: GetAllTagsWithNotesUseCase
    private lateinit var viewModel: NotesViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.e(any(), any(), any()) } returns 0 // Mock Log.e to avoid actual logging
        getAllNotesWithTagUseCase = mockk()
        getAllTagsWithNotesUseCase = mockk()
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `uiState should emit NotesLoaded when use cases return data`() =
        runTest {
            val timesStamp = System.currentTimeMillis()
            // Arrange
            val fakeNotes =
                listOf(
                    NoteWithTag(
                        id = 1,
                        content = "Note 1",
                        timestamp = timesStamp,
                        tagId = 1,
                        done = false,
                        tagName = "Tag 1",
                        tagColor = "#FFFFFF",
                    ),
                )
            val fakeTags =
                listOf(
                    TagWithNotes(
                        id = 1,
                        name = "Tag 1",
                        color = "#FFFFFF",
                        notes =
                            listOf(
                                Note(
                                    id = 1,
                                    content = "Note 1",
                                    timestamp = timesStamp,
                                    tagId = 1,
                                    done = false,
                                ),
                            ),
                    ),
                )

            every { getAllNotesWithTagUseCase() } returns flowOf(fakeNotes)
            every { getAllTagsWithNotesUseCase() } returns (flowOf(fakeTags))
            // Act
            viewModel = NotesViewModel(getAllNotesWithTagUseCase, getAllTagsWithNotesUseCase)

            // Assert
            viewModel.notesUiState.test {
                assertEquals(NotesUiState.Idle, awaitItem())
                val loadedState = awaitItem()
                assertTrue(loadedState is NotesUiState.NotesLoaded)
                val notesLoadedState = loadedState as NotesUiState.NotesLoaded
                assertEquals(fakeNotes, notesLoadedState.notes)
                assertEquals(fakeTags, notesLoadedState.tags)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `uiState should emit Error state when use case throws exception`() =
        runTest {
            // Arrange
            @Suppress("TooGenericExceptionThrown")
            every { getAllNotesWithTagUseCase() } returns flow { throw RuntimeException("Some error") }
            every { getAllTagsWithNotesUseCase() } returns emptyFlow()

            // Act
            viewModel =
                NotesViewModel(
                    getAllNotesWithTagUseCase = getAllNotesWithTagUseCase,
                    getAllTagsWithNotesUseCase = getAllTagsWithNotesUseCase,
                )

            // Assert
            viewModel.notesUiState.test {
                assertEquals(NotesUiState.Idle, awaitItem()) // initial state
                val errorState = awaitItem()
                assertTrue(errorState is NotesUiState.Error)
                assertEquals("Some error", (errorState as NotesUiState.Error).message)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
