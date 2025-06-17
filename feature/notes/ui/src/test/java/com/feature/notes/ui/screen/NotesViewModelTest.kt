package com.feature.notes.ui.screen

import app.cash.turbine.test
import com.feature.notes.domain.model.Note
import com.feature.notes.domain.model.NoteWithTag
import com.feature.notes.domain.model.TagWithNotes
import com.feature.notes.domain.usecase.GetAllNotesWithTagUseCase
import com.feature.notes.domain.usecase.GetAllTagsWithNotesUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class NotesViewModelTest {
    @Mock
    private lateinit var getAllNotesWithTagUseCase: GetAllNotesWithTagUseCase

    @Mock
    private lateinit var getAllTagsWithNotesUseCase: GetAllTagsWithNotesUseCase
    private lateinit var viewModel: NotesViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
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

            whenever(getAllNotesWithTagUseCase()).thenReturn(flowOf(fakeNotes))
            whenever(getAllTagsWithNotesUseCase()).thenReturn(flowOf(fakeTags))

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
            // Arrane
            whenever(getAllNotesWithTagUseCase()).thenThrow(RuntimeException("Some error"))
            whenever(getAllTagsWithNotesUseCase()).thenReturn(emptyFlow())

            // Act
            viewModel = NotesViewModel(getAllNotesWithTagUseCase, getAllTagsWithNotesUseCase)

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
