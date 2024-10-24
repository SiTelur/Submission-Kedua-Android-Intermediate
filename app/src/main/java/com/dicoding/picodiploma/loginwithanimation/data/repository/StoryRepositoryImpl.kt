package com.dicoding.picodiploma.loginwithanimation.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.picodiploma.loginwithanimation.data.Result
import com.dicoding.picodiploma.loginwithanimation.data.local.StoryDatabase
import com.dicoding.picodiploma.loginwithanimation.data.local.StoryPerson
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.remote.StoryRemoteMediator
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.ErrorResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.RegisterResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.UploadStoryResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.retrofit.ApiService
import com.dicoding.picodiploma.loginwithanimation.domain.repository.StoryRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference,
    private val database: StoryDatabase,
) : StoryRepository {

    override fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    override suspend fun logOut() {
        userPreference.logout()
    }


    override fun registerStory(
        name: String,
        email: String,
        password: String,
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val respnse = apiService.registerNew(name = name, email = email, password = password)
            emit(Result.Success(respnse))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
    }

    override fun loginStory(email: String, password: String): LiveData<Result<UserModel>> =
        liveData {
            emit(Result.Loading)

            try {
                val response = apiService.logIn(email, password).loginResult
                userPreference.saveSession(UserModel(email, response.token, true))
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                emit(Result.Error(errorMessage.toString()))
            }
            val data: LiveData<Result<UserModel>> = userPreference.getSession().asLiveData().map {
                Result.Success(it)
            }
            emitSource(data)
        }

    @OptIn(ExperimentalPagingApi::class)
    override fun loadStory(): LiveData<PagingData<StoryPerson>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(database, userPreference, apiService),
            pagingSourceFactory = {
                database.storyDao().getAllQuote()
            }
        ).liveData
    }

    override fun loadStoryWithLocation(): LiveData<Result<List<ListStoryItem>>> = liveData {
        emit(Result.Loading)
        try {
            val token = userPreference.getSession().first().token
            val response = apiService.getStoriesWithLocation("Bearer $token").listStory
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
    }

    override fun uploadStory(
        file: MultipartBody.Part,
        description: RequestBody,
    ): LiveData<Result<UploadStoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            val token = userPreference.getSession().first().token
            val response = apiService.postStory("Bearer $token", file, description)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
    }
}