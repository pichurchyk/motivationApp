package com.pichurchyk.motivationapp.di

import com.pichurchyk.motivationapp.data.db.AchievementsDao
import com.pichurchyk.motivationapp.data.network.QuoteApi
import com.pichurchyk.motivationapp.data.repo.LocalRepository
import com.pichurchyk.motivationapp.data.repo.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRemoteRepository(quoteApi: QuoteApi) : RemoteRepository = RemoteRepository(quoteApi)

    @Provides
    @Singleton
    fun provideLocalRepository(dao: AchievementsDao) : LocalRepository = LocalRepository(dao)
}