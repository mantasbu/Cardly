package com.kotlisoft.cardly.domain.preferences

import android.content.SharedPreferences
import com.kotlisoft.cardly.domain.model.Settings

class DefaultPreferences(
    private val sharedPrefs: SharedPreferences,
): Preferences {
    override fun saveQuestionLocale(locale: String) {
        sharedPrefs.edit()
            .putString(Preferences.KEY_QUESTION_LOCALE, locale)
            .apply()
    }

    override fun saveAnswerLocale(locale: String) {
        sharedPrefs.edit()
            .putString(Preferences.KEY_ANSWER_LOCALE, locale)
            .apply()
    }

    override fun saveSampleDataImport(isSampleDataImported: Boolean) {
        sharedPrefs.edit()
            .putBoolean(Preferences.KEY_SAMPLE_DATA_IMPORT, isSampleDataImported)
            .apply()
    }

    override fun loadSettings(): Settings {
        val questionLocale = sharedPrefs.getString(Preferences.KEY_QUESTION_LOCALE, "US") ?: "US"
        val answerLocale = sharedPrefs.getString(Preferences.KEY_ANSWER_LOCALE, "US") ?: "US"
        val isSampleDataImported = sharedPrefs.getBoolean(Preferences.KEY_SAMPLE_DATA_IMPORT, false)
        return Settings(
            questionLocale = questionLocale,
            answerLocale = answerLocale,
            isSampleDataImported = isSampleDataImported,
        )
    }
}