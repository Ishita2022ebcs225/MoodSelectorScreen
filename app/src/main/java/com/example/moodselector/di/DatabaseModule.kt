package com.example.moodselector.di

import android.content.Context
import androidx.room.Room
import com.example.moodselector.data.local.dao.ABCModelCompletionDao
import com.example.moodselector.data.local.dao.AssessmentResultDao
import com.example.moodselector.data.local.dao.CBTActivityCompletionDao
import com.example.moodselector.data.local.dao.FiveMinuteStarterCompletionDao
import com.example.moodselector.data.local.dao.Grounding54321CompletionDao
import com.example.moodselector.data.local.dao.JournalDao
import com.example.moodselector.data.local.dao.MindfulMeditationCompletionDao
import com.example.moodselector.data.local.dao.MoodDao
import com.example.moodselector.data.local.dao.ScheduledCBTActivityDao
import com.example.moodselector.data.local.dao.SelfCompassionReflectionCompletionDao
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

    /*
     * --------------------------------------------------
     * APP DATABASE
     * --------------------------------------------------
     */

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


    /*
     * --------------------------------------------------
     * MOOD DAO
     * --------------------------------------------------
     */

    @Provides
    fun provideMoodDao(
        database: AppDatabase
    ): MoodDao {

        return database.moodDao()
    }


    /*
     * --------------------------------------------------
     * JOURNAL DAO
     * --------------------------------------------------
     */

    @Provides
    fun provideJournalDao(
        database: AppDatabase
    ): JournalDao {

        return database.journalDao()
    }


    /*
     * --------------------------------------------------
     * ASSESSMENT RESULT DAO
     * --------------------------------------------------
     */

    @Provides
    fun provideAssessmentResultDao(
        database: AppDatabase
    ): AssessmentResultDao {

        return database.assessmentResultDao()
    }


    /*
     * --------------------------------------------------
     * CBT ACTIVITY COMPLETION DAO
     * --------------------------------------------------
     */

    @Provides
    fun provideCBTActivityCompletionDao(
        database: AppDatabase
    ): CBTActivityCompletionDao {

        return database.cbtActivityCompletionDao()
    }


    /*
     * --------------------------------------------------
     * SCHEDULED CBT ACTIVITY DAO
     * --------------------------------------------------
     */

    @Provides
    fun provideScheduledCBTActivityDao(
        database: AppDatabase
    ): ScheduledCBTActivityDao {

        return database.scheduledCBTActivityDao()
    }


    /*
     * --------------------------------------------------
     * FIVE-MINUTE STARTER COMPLETION DAO
     * --------------------------------------------------
     */

    @Provides
    fun provideFiveMinuteStarterCompletionDao(
        database: AppDatabase
    ): FiveMinuteStarterCompletionDao {

        return database.fiveMinuteStarterCompletionDao()
    }


    /*
     * --------------------------------------------------
     * MINDFUL MEDITATION COMPLETION DAO
     * --------------------------------------------------
     */

    @Provides
    fun provideMindfulMeditationCompletionDao(
        database: AppDatabase
    ): MindfulMeditationCompletionDao {

        return database.mindfulMeditationCompletionDao()
    }


    /*
     * --------------------------------------------------
     * 5-4-3-2-1 GROUNDING
     * --------------------------------------------------
     */

    @Provides
    fun provideGrounding54321CompletionDao(
        database: AppDatabase
    ): Grounding54321CompletionDao {

        return database.grounding54321CompletionDao()
    }


    /*
     * --------------------------------------------------
     * ABC MODEL
     * --------------------------------------------------
     */

    @Provides
    fun provideABCModelCompletionDao(
        database: AppDatabase
    ): ABCModelCompletionDao {

        return database.abcModelCompletionDao()
    }


    /*
     * --------------------------------------------------
     * SELF-COMPASSION REFLECTION
     * --------------------------------------------------
     */

    @Provides
    fun provideSelfCompassionReflectionCompletionDao(
        database: AppDatabase
    ): SelfCompassionReflectionCompletionDao {

        return database.selfCompassionReflectionCompletionDao()
    }
}