package com.kotlisoft.cardly.domain.use_case

import com.kotlisoft.cardly.domain.model.Settings
import com.kotlisoft.cardly.domain.preferences.Preferences
import javax.inject.Inject

class GetSettings @Inject constructor(
    private val preferences: Preferences,
) {
    operator fun invoke(): Settings {
        return preferences.loadSettings()
    }
}