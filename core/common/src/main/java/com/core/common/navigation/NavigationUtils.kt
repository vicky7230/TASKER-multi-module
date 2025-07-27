package com.core.common.navigation

import kotlinx.serialization.Serializable

sealed class SubGraph {
    @Serializable
    data object Notes : SubGraph()

    @Serializable
    data object AddEditNote : SubGraph()

    @Serializable
    data object Tags : SubGraph()
}

sealed class Dest {
    @Serializable
    data object Notes : Dest()

    @Serializable
    data class AddEditNote(
        val noteId: Long,
    ) : Dest()

    @Serializable
    data class Tags(
        val tagId: Long,
    ) : Dest()
}
