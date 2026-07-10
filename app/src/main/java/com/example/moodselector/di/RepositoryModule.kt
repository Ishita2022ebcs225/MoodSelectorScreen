package com.example.moodselector.di

import com.example.moodselector.data.repository.AssessmentRepositoryImpl
import com.example.moodselector.data.repository.JournalRepositoryImpl
import com.example.moodselector.data.repository.MoodRepositoryImpl
import com.example.moodselector.domain.repository.AssessmentRepository
import com.example.moodselector.domain.repository.JournalRepository
import com.example.moodselector.domain.repository.MoodRepository
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
}