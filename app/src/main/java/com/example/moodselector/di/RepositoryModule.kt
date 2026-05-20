package com.example.moodselector.di

import com.example.moodselector.data.MoodDao
import com.example.moodselector.repository.MoodRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMoodRepository(
        dao: MoodDao
    ): MoodRepository {
        return MoodRepository(dao)
    }
}
