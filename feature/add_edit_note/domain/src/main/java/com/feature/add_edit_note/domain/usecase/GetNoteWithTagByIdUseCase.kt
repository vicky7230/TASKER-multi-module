package com.feature.add_edit_note.domain.usecase

import com.feature.notes.domain.model.NoteWithTag
import com.feature.notes.domain.repo.NotesRepository
import javax.inject.Inject

class GetNoteWithTagByIdUseCase
    @Inject
    constructor(
        private val repository: NotesRepository,
    ) {
        suspend operator fun invoke(id: Long): NoteWithTag? = repository.getNoteWithTagById(id)
    }
