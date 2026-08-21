package com.example.moodselector.di

import com.example.moodselector.data.repository.ABCModelCompletionRepositoryImpl
import com.example.moodselector.data.repository.AssessmentRepositoryImpl
import com.example.moodselector.data.repository.CBTProgressRepositoryImpl
import com.example.moodselector.data.repository.FiveMinuteStarterCompletionRepositoryImpl
import com.example.moodselector.data.repository.FirebaseAuthRepositoryImpl
import com.example.moodselector.data.repository.FirestoreCloudBackupRepositoryImpl
import com.example.moodselector.data.repository.Grounding54321CompletionRepositoryImpl
import com.example.moodselector.data.repository.JournalRepositoryImpl
import com.example.moodselector.data.repository.MindfulMeditationCompletionRepositoryImpl
import com.example.moodselector.data.repository.MoodRepositoryImpl
import com.example.moodselector.data.repository.ScheduledCBTActivityRepositoryImpl
import com.example.moodselector.data.repository.SelfCompassionReflectionCompletionRepositoryImpl
import com.example.moodselector.data.repository.UserDataDeletionRepositoryImpl

import com.example.moodselector.domain.repository.ABCModelCompletionRepository
import com.example.moodselector.domain.repository.AssessmentRepository
import com.example.moodselector.domain.repository.AuthRepository
import com.example.moodselector.domain.repository.CBTProgressRepository
import com.example.moodselector.domain.repository.CloudBackupRepository
import com.example.moodselector.domain.repository.FiveMinuteStarterCompletionRepository
import com.example.moodselector.domain.repository.Grounding54321CompletionRepository
import com.example.moodselector.domain.repository.JournalRepository
import com.example.moodselector.domain.repository.MindfulMeditationCompletionRepository
import com.example.moodselector.domain.repository.MoodRepository
import com.example.moodselector.domain.repository.ScheduledCBTActivityRepository
import com.example.moodselector.domain.repository.SelfCompassionReflectionCompletionRepository
import com.example.moodselector.domain.repository.UserDataDeletionRepository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMoodRepository(
        impl: MoodRepositoryImpl
    ): MoodRepository


    @Binds
    @Singleton
    abstract fun bindJournalRepository(
        impl: JournalRepositoryImpl
    ): JournalRepository


    @Binds
    @Singleton
    abstract fun bindAssessmentRepository(
        impl: AssessmentRepositoryImpl
    ): AssessmentRepository


    @Binds
    @Singleton
    abstract fun bindCBTProgressRepository(
        impl: CBTProgressRepositoryImpl
    ): CBTProgressRepository


    @Binds
    @Singleton
    abstract fun bindScheduledCBTActivityRepository(
        impl: ScheduledCBTActivityRepositoryImpl
    ): ScheduledCBTActivityRepository


    @Binds
    @Singleton
    abstract fun bindFiveMinuteStarterCompletionRepository(
        impl: FiveMinuteStarterCompletionRepositoryImpl
    ): FiveMinuteStarterCompletionRepository


    @Binds
    @Singleton
    abstract fun bindMindfulMeditationCompletionRepository(
        impl: MindfulMeditationCompletionRepositoryImpl
    ): MindfulMeditationCompletionRepository


    @Binds
    @Singleton
    abstract fun bindGrounding54321CompletionRepository(
        implementation:
        Grounding54321CompletionRepositoryImpl
    ): Grounding54321CompletionRepository


    @Binds
    @Singleton
    abstract fun bindABCModelCompletionRepository(
        implementation:
        ABCModelCompletionRepositoryImpl
    ): ABCModelCompletionRepository


    /*
     * --------------------------------------------------
     * SELF-COMPASSION REFLECTION
     * --------------------------------------------------
     */

    @Binds
    @Singleton
    abstract fun bindSelfCompassionReflectionCompletionRepository(
        implementation:
        SelfCompassionReflectionCompletionRepositoryImpl
    ): SelfCompassionReflectionCompletionRepository


    /*
     * --------------------------------------------------
     * USER DATA DELETION
     * --------------------------------------------------
     */

    @Binds
    @Singleton
    abstract fun bindUserDataDeletionRepository(
        implementation:
        UserDataDeletionRepositoryImpl
    ): UserDataDeletionRepository


    /*
     * --------------------------------------------------
     * FIREBASE AUTHENTICATION
     * --------------------------------------------------
     */

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        implementation:
        FirebaseAuthRepositoryImpl
    ): AuthRepository


    /*
     * --------------------------------------------------
     * FIRESTORE CLOUD BACKUP
     * --------------------------------------------------
     */

    @Binds
    @Singleton
    abstract fun bindCloudBackupRepository(
        implementation:
        FirestoreCloudBackupRepositoryImpl
    ): CloudBackupRepository
}

