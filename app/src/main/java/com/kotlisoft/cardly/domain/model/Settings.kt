package com.kotlisoft.cardly.domain.model

data class Settings(
    val questionLocale: String,
    val answerLocale: String,
    val isSampleDataImported: Boolean,
)
