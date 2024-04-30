package com.kotlisoft.cardly.di

import com.kotlisoft.cardly.data.repository.CardRepositoryImpl
import com.kotlisoft.cardly.domain.repository.CardRepository
import com.kotlisoft.cardly.domain.repository.DeckRepository
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
    abstract fun bindCardRepository(
        cardRepositoryImpl: CardRepositoryImpl
    ): CardRepository

    @Binds
    @Singleton
    abstract fun bindDeckRepository(
        deckRepository: DeckRepository
    ): DeckRepository
}