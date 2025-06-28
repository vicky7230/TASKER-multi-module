package com.feature.tags.ui.di

import com.feature.tags.ui.navigation.TagsApi
import com.feature.tags.ui.navigation.TagsApiImpl
import dagger.Binds
import dagger.Module

@Module
abstract class TagsUiModule {
    @Binds
    abstract fun bindTagsApi(tagsApiImpl: TagsApiImpl): TagsApi
}
