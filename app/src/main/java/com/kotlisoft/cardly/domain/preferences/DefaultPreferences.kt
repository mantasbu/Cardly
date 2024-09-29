package com.kotlisoft.cardly.domain.preferences

import android.content.SharedPreferences
import com.kotlisoft.cardly.domain.model.Settings
import com.kotlisoft.cardly.presentation.ui.settings.SettingLocale

class DefaultPreferences(
    private val sharedPrefs: SharedPreferences,
): Preferences {
    override fun saveQuestionLocale(locale: SettingLocale) {
        sharedPrefs.edit()
            .putString(Preferences.KEY_QUESTION_LOCALE, locale.name)
            .apply()
    }

    override fun saveAnswerLocale(locale: SettingLocale) {
        sharedPrefs.edit()
            .putString(Preferences.KEY_ANSWER_LOCALE, locale.name)
            .apply()
    }

    override fun saveSampleDataImport(isSampleDataImported: Boolean) {
        sharedPrefs.edit()
            .putBoolean(Preferences.KEY_SAMPLE_DATA_IMPORT, isSampleDataImported)
            .apply()
    }

    override fun loadSettings(): Settings {
        val questionLocale = sharedPrefs.getString(Preferences.KEY_QUESTION_LOCALE, SettingLocale.ES.name) ?: SettingLocale.ES.name
        val answerLocale = sharedPrefs.getString(Preferences.KEY_ANSWER_LOCALE, SettingLocale.US.name) ?: SettingLocale.US.name
        val isSampleDataImported = sharedPrefs.getBoolean(Preferences.KEY_SAMPLE_DATA_IMPORT, false)
        return Settings(
            questionLocale = if (questionLocale == SettingLocale.ES.name) SettingLocale.ES else SettingLocale.US,
            answerLocale = if (answerLocale == SettingLocale.ES.name) SettingLocale.ES else SettingLocale.US,
            isSampleDataImported = isSampleDataImported,
        )
    }
}