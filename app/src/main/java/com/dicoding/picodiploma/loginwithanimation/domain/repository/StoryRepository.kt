package com.dicoding.picodiploma.loginwithanimation.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.dicoding.picodiploma.loginwithanimation.data.Result
import com.dicoding.picodiploma.loginwithanimation.data.local.StoryPerson
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.RegisterResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.UploadStoryResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface StoryRepository {
    fun getSession(): Flow<UserModel>
    suspend fun logOut()
    fun registerStory(
        name: String,
        email: String,
        password: String,
    ): LiveData<Result<RegisterResponse>>

    fun loginStory(email: String, password: String): LiveData<Result<UserModel>>
    fun loadStory(): LiveData<PagingData<StoryPerson>>
    fun loadStoryWithLocation(): LiveData<Result<List<ListStoryItem>>>
    fun uploadStory(
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?
    ): LiveData<Result<UploadStoryResponse>>
}