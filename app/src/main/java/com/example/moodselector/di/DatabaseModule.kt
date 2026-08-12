package com.example.moodselector.di

import android.content.Context
import androidx.room.Room
import com.example.moodselector.data.local.dao.AssessmentResultDao
import com.example.moodselector.data.local.dao.CBTActivityCompletionDao
import com.example.moodselector.data.local.dao.JournalDao
import com.example.moodselector.data.local.dao.MoodDao
import com.example.moodselector.data.local.database.AppDatabase
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
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {

        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "moodselector_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMoodDao(
        database: AppDatabase
    ): MoodDao {
        return database.moodDao()
    }

    @Provides
    fun provideJournalDao(
        database: AppDatabase
    ): JournalDao {
        return database.journalDao()
    }

    @Provides
    fun provideAssessmentResultDao(
        database: AppDatabase
    ): AssessmentResultDao {
        return database.assessmentResultDao()
    }

    @Provides
    fun provideCBTActivityCompletionDao(
        database: AppDatabase
    ): CBTActivityCompletionDao {
        return database.cbtActivityCompletionDao()
    }
}