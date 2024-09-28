package com.kotlisoft.cardly.domain.use_case

import com.kotlisoft.cardly.domain.preferences.Preferences
import javax.inject.Inject

class UpdateAnswerLocale @Inject constructor(
    private val preferences: Preferences,
) {
    operator fun invoke(locale: String) {
        preferences.saveAnswerLocale(locale)
    }
}