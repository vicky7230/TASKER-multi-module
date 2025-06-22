package com.feature.add_edit_note.domain.usecase

import com.core.domain.model.NoteWithTag
import com.core.domain.repo.NotesRepository
import javax.inject.Inject

class UpsertNotesUseCase
    @Inject
    constructor(
        private val repository: NotesRepository,
    ) {
        suspend operator fun invoke(notes: List<NoteWithTag>) {
            repository.upsertNotes(notes)
        }
    }
