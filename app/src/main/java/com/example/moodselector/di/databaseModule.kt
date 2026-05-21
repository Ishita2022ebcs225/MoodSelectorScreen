package com.example.moodselector.di

import android.content.Context
import androidx.room.Room
import com.example.moodselector.data.local.MoodDao
import com.example.moodselector.data.local.MoodDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MoodDatabase {
        return Room.databaseBuilder(
            context,
            MoodDatabase::class.java,
            "mood_database"
        ).build()
    }

    @Provides
    fun provideMoodDao(
        database: MoodDatabase
    ): MoodDao {
        return database.moodDao()
    }
}