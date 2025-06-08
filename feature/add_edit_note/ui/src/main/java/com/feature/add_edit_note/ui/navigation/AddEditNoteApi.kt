package com.feature.add_edit_note.ui.navigation

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.core.feature_api.FeatureApi
import javax.inject.Inject

interface AddEditNoteApi : FeatureApi

class AddEditNoteApiImpl @Inject constructor() : AddEditNoteApi {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder,
        viewModelFactory: ViewModelProvider.Factory
    ) {
        InternalAddEditNoteApi.registerGraph(navHostController, navGraphBuilder, viewModelFactory)
    }
}