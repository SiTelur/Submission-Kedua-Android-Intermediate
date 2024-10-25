package com.dicoding.picodiploma.loginwithanimation.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class StoryPerson(
    val photoUrl: String,
    val createdAt: String,
    val name: String,
    val description: String,
    val lon: Double,
    @PrimaryKey
    val id: String,
    val lat: Double,
):Parcelable


