package com.kotlisoft.cardly.domain.repository

import com.kotlisoft.cardly.domain.model.Card

interface CardRepository {
    fun getCards(): List<Card>

    fun getCardById(id: Int): Card

    fun insertCard(card: Card)

    fun deleteCard(card: Card)
}