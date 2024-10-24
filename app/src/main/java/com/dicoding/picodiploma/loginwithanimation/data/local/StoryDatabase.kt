package com.dicoding.picodiploma.loginwithanimation.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StoryPerson::class,RemoteKeys::class], version = 2, exportSchema = false)
abstract class StoryDatabase: RoomDatabase() {
    abstract fun storyDao(): StoryDao
    abstract  fun remoteKeysDao() : RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: StoryDatabase?  = null

        fun getDatabase(context: Context) : StoryDatabase {
            if (INSTANCE == null) {
                synchronized(StoryDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        StoryDatabase::class.java,"story_person")
                        .build()
                }
            }
            return INSTANCE as StoryDatabase
        }
    }
}