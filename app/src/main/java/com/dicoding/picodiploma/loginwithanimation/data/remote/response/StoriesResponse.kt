package com.dicoding.picodiploma.loginwithanimation.data.remote.response

import android.os.Parcelable
import com.dicoding.picodiploma.loginwithanimation.data.local.StoryPerson
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class StoriesResponse(

    @field:SerializedName("listStory")
    val listStory: List<ListStoryItem>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,
)

@Parcelize
data class ListStoryItem(

    @field:SerializedName("photoUrl")
    val photoUrl: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("lon")
    val lon: Double,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("lat")
    val lat: Double,
) : Parcelable

fun List<ListStoryItem>.toStoryPersonList(): List<StoryPerson> {
    return this.map { listStoryItem ->
        StoryPerson(
            photoUrl = listStoryItem.photoUrl,
            createdAt = listStoryItem.createdAt,
            name = listStoryItem.name,
            description = listStoryItem.description,
            lon = listStoryItem.lon,
            id = listStoryItem.id,
            lat = listStoryItem.lat
        )
    }
}
