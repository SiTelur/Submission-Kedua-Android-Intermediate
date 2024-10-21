package com.dicoding.picodiploma.loginwithanimation.view.login

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.StoryRepository

class LoginViewModel(private val repository: StoryRepository) : ViewModel() {
    fun loginStory(email: String, password: String) = repository.loginStory(email, password)
}