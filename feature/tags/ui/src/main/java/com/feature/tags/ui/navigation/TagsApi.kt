package com.feature.tags.ui.navigation

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.core.feature_api.FeatureApi
import javax.inject.Inject

interface TagsApi : FeatureApi

class TagsApiImpl
    @Inject
    internal constructor(
        private val internalTagsFeatureApi: InternalTagsFeatureApi,
    ) : TagsApi {
        override fun registerGraph(
            navHostController: NavHostController,
            navGraphBuilder: NavGraphBuilder,
            viewModelFactory: ViewModelProvider.Factory,
        ) {
            internalTagsFeatureApi.registerGraph(
                navHostController,
                navGraphBuilder,
                viewModelFactory,
            )
        }
    }
