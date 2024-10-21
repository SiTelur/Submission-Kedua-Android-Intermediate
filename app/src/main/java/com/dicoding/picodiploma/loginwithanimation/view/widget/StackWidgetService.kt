package com.dicoding.picodiploma.loginwithanimation.view.widget

import android.content.Intent
import android.widget.RemoteViewsService
import com.dicoding.picodiploma.loginwithanimation.data.StoryRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.pref.dataStore
import com.dicoding.picodiploma.loginwithanimation.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class StackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        val pref = UserPreference.getInstance(this.applicationContext.dataStore)
        val apiService = ApiConfig.getApiService()
        return StackRemoteViewFactory(this.applicationContext, apiService,pref)
    }
}