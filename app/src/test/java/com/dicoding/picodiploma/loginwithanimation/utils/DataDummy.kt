package com.dicoding.picodiploma.loginwithanimation.utils

import com.dicoding.picodiploma.loginwithanimation.data.local.StoryPerson

object DataDummy {
    fun generateDummyQuoteResponse() : List<StoryPerson> {
        val items: MutableList<StoryPerson> = arrayListOf()
        for (i in 10..100){
            val quote = StoryPerson(
                id = i.toString(),
                name = "name + $i",
                description = "description $i",
                createdAt = "createdAt $i",
                photoUrl = "photoUrl $i",
                lon = i.toDouble(),
                lat = i.toDouble(),
            )
            items.add(quote)
        }
        return items
    }
}