package com.dicoding.picodiploma.loginwithanimation.view.login

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.domain.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: StoryRepository) : ViewModel() {
    fun loginStory(email: String, password: String) = repository.loginStory(email, password)
}