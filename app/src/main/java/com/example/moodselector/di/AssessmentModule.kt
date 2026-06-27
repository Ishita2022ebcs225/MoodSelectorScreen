package com.example.moodselector.di

import com.example.moodselector.data.assessment.provider.AssessmentDefinitionProvider
import com.example.moodselector.data.assessment.provider.DefaultAssessmentDefinitionProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AssessmentModule {

    @Binds
    @Singleton
    abstract fun bindAssessmentDefinitionProvider(
        provider: DefaultAssessmentDefinitionProvider
    ): AssessmentDefinitionProvider
}