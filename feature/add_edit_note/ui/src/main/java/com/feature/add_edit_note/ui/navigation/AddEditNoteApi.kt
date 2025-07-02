package com.feature.add_edit_note.ui.navigation

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.core.feature_api.FeatureApi
import javax.inject.Inject

interface AddEditNoteApi : FeatureApi

class AddEditNoteApiImpl
    @Inject
    internal constructor(
        private val internalAddEditNoteApi: InternalAddEditNoteApi,
    ) : AddEditNoteApi {
        override fun registerGraph(
            navHostController: NavHostController,
            navGraphBuilder: NavGraphBuilder,
            viewModelFactory: ViewModelProvider.Factory,
        ) {
            internalAddEditNoteApi.registerGraph(
                navHostController,
                navGraphBuilder,
                viewModelFactory,
            )
        }
    }
