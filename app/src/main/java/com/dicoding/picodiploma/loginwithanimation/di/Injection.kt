package com.dicoding.picodiploma.loginwithanimation.di

import android.content.Context
import com.dicoding.picodiploma.loginwithanimation.data.StoryRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.pref.dataStore
import com.dicoding.picodiploma.loginwithanimation.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.first

import kotlinx.coroutines.runBlocking

object Injection {   fun provideRepository(context: Context): StoryRepository {
    val pref = UserPreference.getInstance(context.dataStore)
    val apiService = ApiConfig.getApiService()
    return StoryRepository.getInstance(apiService, pref)
}
}