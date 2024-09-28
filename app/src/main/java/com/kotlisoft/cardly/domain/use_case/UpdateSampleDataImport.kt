package com.kotlisoft.cardly.domain.use_case

import com.kotlisoft.cardly.domain.preferences.Preferences
import javax.inject.Inject

class UpdateSampleDataImport @Inject constructor(
    private val preferences: Preferences,
) {
    operator fun invoke(isSampleDataImport: Boolean) {
        preferences.saveSampleDataImport(isSampleDataImport)
    }
}