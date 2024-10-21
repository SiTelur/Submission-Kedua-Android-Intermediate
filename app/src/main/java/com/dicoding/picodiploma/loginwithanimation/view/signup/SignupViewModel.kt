package com.dicoding.picodiploma.loginwithanimation.view.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.StoryRepository
import kotlinx.coroutines.launch

class SignupViewModel(private val repository: StoryRepository): ViewModel() {
     fun registerNew(name: String, email: String, password: String) =
        repository.registerStory(name,email,password)


}