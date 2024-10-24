package com.dicoding.picodiploma.loginwithanimation.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: List<StoryPerson>)

    @Query("SELECT * FROM StoryPerson")
    fun getAllQuote(): PagingSource<Int, StoryPerson>

    @Query("DELETE FROM StoryPerson")
    suspend fun deleteAll()
}