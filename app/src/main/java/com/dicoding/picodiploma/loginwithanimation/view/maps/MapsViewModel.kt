package com.dicoding.picodiploma.loginwithanimation.view.maps

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.domain.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(private val repository: StoryRepository): ViewModel() {
    fun getStoriesWithLocation() = repository.loadStoryWithLocation()
}