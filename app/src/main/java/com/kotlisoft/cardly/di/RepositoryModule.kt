package com.kotlisoft.cardly.di

import android.content.Context
import com.kotlisoft.cardly.data.local.AssetDataSource
import com.kotlisoft.cardly.data.local.CardsDatabase
import com.kotlisoft.cardly.data.repository.CardRepositoryImpl
import com.kotlisoft.cardly.data.repository.DeckRepositoryImpl
import com.kotlisoft.cardly.data.repository.SampleDataRepositoryImpl
import com.kotlisoft.cardly.domain.preferences.Preferences
import com.kotlisoft.cardly.domain.repository.CardRepository
import com.kotlisoft.cardly.domain.repository.DeckRepository
import com.kotlisoft.cardly.domain.repository.SampleDataRepository
import com.kotlisoft.cardly.domain.use_case.AddCard
import com.kotlisoft.cardly.domain.use_case.AddDeck
import com.kotlisoft.cardly.domain.use_case.CardUseCases
import com.kotlisoft.cardly.domain.use_case.DataImportUseCase
import com.kotlisoft.cardly.domain.use_case.DeckUseCases
import com.kotlisoft.cardly.domain.use_case.DeleteAllCards
import com.kotlisoft.cardly.domain.use_case.DeleteAllCardsByDeckName
import com.kotlisoft.cardly.domain.use_case.DeleteAllDecks
import com.kotlisoft.cardly.domain.use_case.DeleteCard
import com.kotlisoft.cardly.domain.use_case.DeleteDeckByName
import com.kotlisoft.cardly.domain.use_case.GetCard
import com.kotlisoft.cardly.domain.use_case.GetCardsByDeck
import com.kotlisoft.cardly.domain.use_case.GetDeckWithCards
import com.kotlisoft.cardly.domain.use_case.GetDecks
import com.kotlisoft.cardly.domain.use_case.GetSettings
import com.kotlisoft.cardly.domain.use_case.PreferencesUseCases
import com.kotlisoft.cardly.domain.use_case.UpdateAnswerLocale
import com.kotlisoft.cardly.domain.use_case.UpdateCard
import com.kotlisoft.cardly.domain.use_case.UpdateCardLevel
import com.kotlisoft.cardly.domain.use_case.UpdateDeckName
import com.kotlisoft.cardly.domain.use_case.UpdateQuestionLocale
import com.kotlisoft.cardly.domain.use_case.UpdateSampleDataImport
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideSampleDataRepository(
        assetDataSource: AssetDataSource,
    ): SampleDataRepository {
        return SampleDataRepositoryImpl(assetDataSource)
    }

    @Provides
    @Singleton
    fun provideAssetDataSource(@ApplicationContext context: Context): AssetDataSource {
        return AssetDataSource(context)
    }

    @Provides
    @Singleton
    fun provideDeckUseCases(deckRepository: DeckRepository): DeckUseCases {
        return DeckUseCases(
            addDeck = AddDeck(deckRepository),
            deleteDeckByName = DeleteDeckByName(deckRepository),
            deleteAllCardsByDeckName = DeleteAllCardsByDeckName(deckRepository),
            updateDeckName = UpdateDeckName(deckRepository),
            getDecks = GetDecks(deckRepository),
            deleteAllDecks = DeleteAllDecks(deckRepository)
        )
    }

    @Provides
    @Singleton
    fun provideCardUseCases(cardRepository: CardRepository): CardUseCases {
        return CardUseCases(
            addCard = AddCard(cardRepository),
            getCard = GetCard(cardRepository),
            deleteCard = DeleteCard(cardRepository),
            getCardsByDeck = GetCardsByDeck(cardRepository),
            getDeckWithCards = GetDeckWithCards(cardRepository),
            updateCard = UpdateCard(cardRepository),
            updateCardLevel = UpdateCardLevel(cardRepository),
            deleteAllCards = DeleteAllCards(cardRepository)
        )
    }

    @Provides
    @Singleton
    fun provideDataImportUseCase(sampleDataRepository: SampleDataRepository): DataImportUseCase {
        return DataImportUseCase(repository = sampleDataRepository)
    }

    @Provides
    @Singleton
    fun providePreferencesUseCases(preferences: Preferences): PreferencesUseCases {
        return PreferencesUseCases(
            updateQuestionLocale = UpdateQuestionLocale(preferences),
            updateAnswerLocale = UpdateAnswerLocale(preferences),
            updateSampleDataImport = UpdateSampleDataImport(preferences),
            getSettings = GetSettings(preferences),
        )
    }
}