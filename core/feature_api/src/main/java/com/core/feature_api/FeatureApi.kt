package com.core.feature_api

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface FeatureApi {
    fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder,
        viewModelFactory: ViewModelProvider.Factory
    )
}