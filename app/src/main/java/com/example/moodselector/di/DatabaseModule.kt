package com.example.moodselector.di

import android.content.Context
import androidx.room.Room
import com.example.moodselector.data.local.dao.JournalDao
import com.example.moodselector.data.local.dao.MoodDao
import com.example.moodselector.data.local.database.JournalDatabase
import com.example.moodselector.data.local.database.MoodDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    // ---------------- MOOD DATABASE ----------------

    @Provides
    @Singleton
    fun provideMoodDatabase(
        @ApplicationContext context: Context
    ): MoodDatabase {

        return Room.databaseBuilder(
            context,
            MoodDatabase::class.java,
            "mood_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMoodDao(
        database: MoodDatabase
    ): MoodDao {
        return database.moodDao()
    }

    // ---------------- JOURNAL DATABASE ----------------

    @Provides
    @Singleton
    fun provideJournalDatabase(
        @ApplicationContext context: Context
    ): JournalDatabase {

        return Room.databaseBuilder(
            context,
            JournalDatabase::class.java,
            "journal_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideJournalDao(
        database: JournalDatabase
    ): JournalDao {
        return database.journalDao()
    }
}