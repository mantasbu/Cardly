package com.kotlisoft.cardly.data.mappers

import com.kotlisoft.cardly.data.local.CardEntity
import com.kotlisoft.cardly.domain.model.Card

fun CardEntity.toCard(): Card {
    return Card(
        question = question,
        answer = answer,
    )
}
