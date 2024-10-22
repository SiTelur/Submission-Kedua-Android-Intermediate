package com.dicoding.picodiploma.loginwithanimation.view.maps

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.StoryRepository

class MapsViewModel(private val repository: StoryRepository): ViewModel() {
    fun getStoriesWithLocation() = repository.loadStoryWithLocation()
}