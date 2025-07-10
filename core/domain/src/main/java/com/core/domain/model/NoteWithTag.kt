package com.core.domain.model

data class NoteWithTag(
    val id: Long = 0,
    val content: String,
    val timestamp: Long,
    val done: Boolean,
    val date: String,
    val time: String,
    val tagId: Long,
    val tagName: String,
    val tagColor: String,
    val optionRevealed: Boolean = false,
)
