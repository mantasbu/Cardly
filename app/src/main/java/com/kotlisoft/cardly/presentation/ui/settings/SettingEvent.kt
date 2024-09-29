package com.kotlisoft.cardly.presentation.ui.settings

sealed class SettingEvent {
    data object ImportSampleData: SettingEvent()
    data object DeleteAllData: SettingEvent()
    data object ConfirmDeleteAllData: SettingEvent()
    data object CancelDeleteAllData: SettingEvent()
    data class SwitchQuestionLocale(val newLocale: SettingLocale): SettingEvent()
    data class SwitchAnswerLocale(val newLocale: SettingLocale): SettingEvent()
}