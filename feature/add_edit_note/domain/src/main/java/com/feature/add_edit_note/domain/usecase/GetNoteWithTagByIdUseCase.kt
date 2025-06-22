package com.feature.add_edit_note.domain.usecase

import com.core.domain.model.NoteWithTag
import com.core.domain.repo.NotesRepository
import javax.inject.Inject

class GetNoteWithTagByIdUseCase
    @Inject
    constructor(
        private val repository: NotesRepository,
    ) {
        suspend operator fun invoke(id: Long): NoteWithTag? = repository.getNoteWithTagById(id)
    }
