package com.kotlisoft.cardly.domain.model

data class Deck(
    val name: String,
)

class InvalidDeckException(message: String): Exception(message)
