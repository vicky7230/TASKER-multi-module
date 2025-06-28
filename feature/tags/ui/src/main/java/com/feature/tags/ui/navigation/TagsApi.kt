package com.feature.tags.ui.navigation

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.core.feature_api.FeatureApi
import javax.inject.Inject

interface TagsApi : FeatureApi

class TagsApiImpl
    @Inject
    constructor() : TagsApi {
        override fun registerGraph(
            navHostController: NavHostController,
            navGraphBuilder: NavGraphBuilder,
            viewModelFactory: ViewModelProvider.Factory,
        ) {
            InternalTagsFeatureApi.registerGraph(navHostController, navGraphBuilder, viewModelFactory)
        }
    }
