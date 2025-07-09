package com.feature.notes.ui.screen

import android.util.Log
import androidx.compose.ui.graphics.Color
import app.cash.turbine.test
import com.core.common.utils.toHexString
import com.core.domain.model.Note
import com.core.domain.model.NoteWithTag
import com.core.domain.model.TagWithNotes
import com.core.domain.usecase.GetAllTagsWithNotesUseCase
import com.core.domain.usecase.UpdateNoteDoneUseCase
import com.feature.notes.domain.usecase.CreateTagUseCase
import com.feature.notes.domain.usecase.GetAllNotesWithTagUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.collections.immutable.persistentListOf
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalCoroutinesApi::class)
class NotesViewModelTest {
    private lateinit var getAllNotesWithTagUseCase: GetAllNotesWithTagUseCase
    private lateinit var getAllTagsWithNotesUseCase: GetAllTagsWithNotesUseCase
    private lateinit var createTagUseCase: CreateTagUseCase
    private lateinit var updateNoteDoneUseCase: UpdateNoteDoneUseCase
    private lateinit var viewModel: NotesViewModel
    private val testDispatcher = StandardTestDispatcher()

    val timesStamp = System.currentTimeMillis()
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
                date = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
                time = "00:00:00",
            ),
        )
    val fakeTags =
        listOf(
            TagWithNotes(
                id = 1,
                name = "Tag 1",
                color = "#FFFFFF",
                notes =
                    persistentListOf(
                        Note(
                            id = 1,
                            content = "Note 1",
                            timestamp = timesStamp,
                            tagId = 1,
                            done = false,
                            date = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
                            time = "00:00:00",
                        ),
                    ),
            ),
        )

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.e(any(), any(), any()) } returns 0 // Mock Log.e to avoid actual logging
        getAllNotesWithTagUseCase = mockk()
        getAllTagsWithNotesUseCase = mockk()
        updateNoteDoneUseCase = mockk()
        createTagUseCase = mockk()
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `uiState should emit NotesLoaded when use cases return data`() =
        runTest {
            // Arrange
            every { getAllNotesWithTagUseCase() } returns flowOf(fakeNotes)
            every { getAllTagsWithNotesUseCase() } returns flowOf(fakeTags)
            // Act
            viewModel =
                NotesViewModel(
                    getAllNotesWithTagUseCase = getAllNotesWithTagUseCase,
                    getAllTagsWithNotesUseCase = getAllTagsWithNotesUseCase,
                    createTagUseCase = createTagUseCase,
                    updateNoteDoneUseCase = updateNoteDoneUseCase,
                )

            // Assert
            viewModel.notesUiState.test {
                assertEquals(NotesUiState.Loading, awaitItem())
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
                    createTagUseCase = createTagUseCase,
                    updateNoteDoneUseCase = updateNoteDoneUseCase,
                )

            // Assert
            viewModel.notesUiState.test {
                assertEquals(NotesUiState.Loading, awaitItem()) // initial state
                val errorState = awaitItem()
                assertTrue(errorState is NotesUiState.Error)
                assertEquals("Error loading notes", (errorState as NotesUiState.Error).message)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `showCreateTagBottomSheet should update uiState to ShowCreateTagBottomSheet`() =
        runTest {
            // Arrange
            every { getAllNotesWithTagUseCase() } returns flowOf(fakeNotes)
            every { getAllTagsWithNotesUseCase() } returns flowOf(fakeTags)

            viewModel =
                NotesViewModel(
                    getAllNotesWithTagUseCase = getAllNotesWithTagUseCase,
                    getAllTagsWithNotesUseCase = getAllTagsWithNotesUseCase,
                    createTagUseCase = createTagUseCase,
                    updateNoteDoneUseCase = updateNoteDoneUseCase,
                )

            // // Act & Assert
            viewModel.notesUiState.test {
                assertEquals(NotesUiState.Loading, awaitItem())
                val loadedState = awaitItem()
                assertTrue(loadedState is NotesUiState.NotesLoaded)
                viewModel.showCreateTagBottomSheet(NotesUiBottomSheet.CreateTagBottomSheet(Color.Unspecified.toHexString()))
                val state = awaitItem()
                assertTrue(state is NotesUiState.NotesLoaded)
                assertEquals(
                    (state as NotesUiState.NotesLoaded).bottomSheet,
                    NotesUiBottomSheet.CreateTagBottomSheet(Color.Unspecified.toHexString()),
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
