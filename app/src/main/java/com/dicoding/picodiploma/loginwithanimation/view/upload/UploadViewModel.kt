package com.dicoding.picodiploma.loginwithanimation.view.upload

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.domain.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(private val repository: StoryRepository) : ViewModel() {
    fun uploadStory(file: MultipartBody.Part, description: RequestBody,lat : RequestBody?,lon : RequestBody?) =
        repository.uploadStory(file, description,lat,lon)
}