package com.feature.tags.ui.di

import com.feature.tags.ui.navigation.TagsFeature
import com.feature.tags.ui.navigation.TagsFeatureImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class TagsUiModule {
    @Binds
    abstract fun bindTagsApi(tagsApiImpl: TagsFeatureImpl): TagsFeature
}
