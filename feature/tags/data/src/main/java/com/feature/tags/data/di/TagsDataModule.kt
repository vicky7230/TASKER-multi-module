package com.feature.tags.data.di

import com.core.domain.repo.TagsRepository
import com.feature.tags.data.repo.TagsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class TagsDataModule {
    @Binds
    abstract fun bindTagsRepository(tagsRepositoryImpl: TagsRepositoryImpl): TagsRepository
}
