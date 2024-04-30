package com.kotlisoft.cardly.di

import android.app.Application
import androidx.room.Room
import com.kotlisoft.cardly.data.local.CardsDatabase
import com.kotlisoft.cardly.data.local.CardsDatabase.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideCardsDatabase(app: Application): CardsDatabase {
        return Room.databaseBuilder(
            app,
            CardsDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}