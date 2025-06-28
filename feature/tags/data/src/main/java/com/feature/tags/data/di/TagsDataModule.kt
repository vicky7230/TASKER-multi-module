package com.feature.tags.data.di

import com.core.domain.repo.TagsRepository
import com.feature.tags.data.repo.TagsRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class TagsDataModule {
    @Binds
    abstract fun bindTagsRepository(tagsRepositoryImpl: TagsRepositoryImpl): TagsRepository
}
