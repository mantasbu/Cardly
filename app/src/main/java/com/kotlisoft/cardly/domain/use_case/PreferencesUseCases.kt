package com.kotlisoft.cardly.domain.use_case

data class PreferencesUseCases(
    val updateQuestionLocale: UpdateQuestionLocale,
    val updateAnswerLocale: UpdateAnswerLocale,
    val updateSampleDataImport: UpdateSampleDataImport,
    val getSettings: GetSettings,
)