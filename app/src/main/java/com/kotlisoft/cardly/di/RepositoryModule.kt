package com.kotlisoft.cardly.di

import com.kotlisoft.cardly.data.local.CardsDatabase
import com.kotlisoft.cardly.data.repository.CardRepositoryImpl
import com.kotlisoft.cardly.data.repository.DeckRepositoryImpl
import com.kotlisoft.cardly.domain.repository.CardRepository
import com.kotlisoft.cardly.domain.repository.DeckRepository
import com.kotlisoft.cardly.domain.use_case.AddDeck
import com.kotlisoft.cardly.domain.use_case.DeckUseCases
import com.kotlisoft.cardly.domain.use_case.DeleteDeckByName
import com.kotlisoft.cardly.domain.use_case.GetDecks
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
    fun provideCardRepository(
        cardsDatabase: CardsDatabase,
    ): CardRepository {
        return CardRepositoryImpl(cardsDatabase.cardDao)
    }

    @Provides
    @Singleton
    fun provideDeckRepository(
        cardsDatabase: CardsDatabase,
    ): DeckRepository {
        return DeckRepositoryImpl(cardsDatabase.deckDao)
    }

    @Provides
    @Singleton
    fun provideDeckUseCases(deckRepository: DeckRepository): DeckUseCases {
        return DeckUseCases(
            addDeck = AddDeck(deckRepository),
            deleteDeckByName = DeleteDeckByName(deckRepository),
            getDecks = GetDecks(deckRepository),
        )
    }
}