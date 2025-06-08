package com.feature.notes.ui.navigation

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.core.feature_api.FeatureApi
import javax.inject.Inject

interface NotesApi: FeatureApi

class NotesApiImpl @Inject constructor(): NotesApi {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder,
        viewModelFactory: ViewModelProvider.Factory
    ) {
        InternalNotesFeatureApi.registerGraph(navHostController, navGraphBuilder, viewModelFactory)
    }
}

