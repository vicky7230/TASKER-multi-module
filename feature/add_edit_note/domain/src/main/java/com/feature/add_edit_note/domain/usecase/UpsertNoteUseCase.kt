package com.feature.add_edit_note.domain.usecase

import com.feature.notes.domain.model.Note
import com.feature.notes.domain.repo.NotesRepository
import javax.inject.Inject

class UpsertNoteUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.upsertNote(note)
    }
}