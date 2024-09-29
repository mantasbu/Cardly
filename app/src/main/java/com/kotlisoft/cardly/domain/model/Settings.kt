package com.kotlisoft.cardly.domain.model

import com.kotlisoft.cardly.presentation.ui.settings.SettingLocale

data class Settings(
    val questionLocale: SettingLocale,
    val answerLocale: SettingLocale,
    val isSampleDataImported: Boolean,
)
