package com.dicoding.picodiploma.loginwithanimation.di

import android.app.Application
import com.dicoding.picodiploma.loginwithanimation.data.local.StoryDao
import com.dicoding.picodiploma.loginwithanimation.data.local.StoryDatabase
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.pref.dataStore
import com.dicoding.picodiploma.loginwithanimation.data.remote.retrofit.ApiConfig
import com.dicoding.picodiploma.loginwithanimation.data.remote.retrofit.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Injection {

    @Provides
    @Singleton
    fun provideApiService() : ApiService {
        return ApiConfig.getApiService()
    }

    @Provides
    @Singleton
    fun provideUserPreference(context: Application) : UserPreference{
        return UserPreference.getInstance(context.dataStore)
    }

    @Provides
    @Singleton
    fun provideStoryDatabase(context: Application): StoryDatabase {
        return StoryDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideStoryDao(database: StoryDatabase) : StoryDao {
        return database.storyDao()
    }
}