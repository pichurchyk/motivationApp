package com.pichurchyk.motivationapp.di

import android.content.Context
import androidx.room.Room
import com.pichurchyk.motivationapp.data.db.AchievementsDao
import com.pichurchyk.motivationapp.data.db.AchievementsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AchievementsDatabase::class.java,
            "Achievements-DB"
        ).build()

    @Provides
    @Singleton
    fun provideDao(db: AchievementsDatabase) : AchievementsDao = db.dao()
}