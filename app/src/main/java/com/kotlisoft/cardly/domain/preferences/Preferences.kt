package com.kotlisoft.cardly.domain.preferences

import com.kotlisoft.cardly.domain.model.Settings
import com.kotlisoft.cardly.presentation.ui.settings.SettingLocale

interface Preferences {
    fun saveQuestionLocale(locale: SettingLocale)
    fun saveAnswerLocale(locale: SettingLocale)
    fun saveSampleDataImport(isSampleDataImported: Boolean)
    fun loadSettings(): Settings

    companion object {
        const val KEY_QUESTION_LOCALE = "question_locale"
        const val KEY_ANSWER_LOCALE = "answer_locale"
        const val KEY_SAMPLE_DATA_IMPORT = "sample_data_import"
    }
}