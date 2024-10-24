package com.dicoding.picodiploma.loginwithanimation.di

import com.dicoding.picodiploma.loginwithanimation.data.repository.StoryRepositoryImpl
import com.dicoding.picodiploma.loginwithanimation.domain.repository.StoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindStoryRepository(
        storyRepositoryImpl: StoryRepositoryImpl
    ) : StoryRepository
}