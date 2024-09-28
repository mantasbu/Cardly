package com.kotlisoft.cardly.presentation.ui.settings

data class SettingsState(
    val isLoadingSampleData: Boolean = false,
    val isSampleDataImported: Boolean = false,
    val isDeleteAllDataDialogVisible: Boolean = false,
    val questionLocale: String = "US",
    val answerLocale: String = "US",
)
