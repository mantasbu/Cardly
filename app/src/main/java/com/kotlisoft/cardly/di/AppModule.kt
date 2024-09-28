package com.kotlisoft.cardly.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.kotlisoft.cardly.data.local.CardsDatabase
import com.kotlisoft.cardly.data.local.CardsDatabase.Companion.DATABASE_NAME
import com.kotlisoft.cardly.domain.preferences.DefaultPreferences
import com.kotlisoft.cardly.domain.preferences.Preferences
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

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("shared_prefs", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences): Preferences {
        return DefaultPreferences(sharedPreferences)
    }
}