package com.kotlisoft.cardly.presentation.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kotlisoft.cardly.R
import com.kotlisoft.cardly.presentation.ui.components.DeleteDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val settingsState by remember(viewModel.state.value) {
        mutableStateOf(viewModel.state.value)
    }
    val isDeleteAllDataDialogVisible = viewModel.state.value.isDeleteAllDataDialogVisible

    if (isDeleteAllDataDialogVisible) {
        DeleteDialog(
            titleText = stringResource(id = R.string.delete_all_data),
            contentText = stringResource(R.string.delete_all_data_content),
            onDismissRequest = {
                viewModel.onEvent(SettingEvent.CancelDeleteAllData)
            },
            onConfirmRequest = {
                viewModel.onEvent(SettingEvent.ConfirmDeleteAllData)
            },
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.clickable { onNavigateBack() },
                    )
                },
                title = {
                    Text(text = stringResource(R.string.settings))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
        content = {
            Column(modifier = Modifier.padding(it)) {
                SettingSwitch(
                    currentLocale = settingsState.questionLocale,
                    settingName = R.string.question_cards_voice,
                    onCheckedChange = { newLocale ->
                        viewModel.onEvent(SettingEvent.SwitchQuestionLocale(newLocale = newLocale))
                    },
                )
                SettingSwitch(
                    currentLocale = settingsState.answerLocale,
                    settingName = R.string.answer_cards_voice,
                    onCheckedChange = { newLocale ->
                        viewModel.onEvent(SettingEvent.SwitchAnswerLocale(newLocale = newLocale))
                    },
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    if (settingsState.isLoadingSampleData) {
                        CircularProgressIndicator()
                    }
                }
            }
        },
        bottomBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                if (!settingsState.isSampleDataImported) {
                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                        enabled = !settingsState.isLoadingSampleData,
                        onClick = {
                            viewModel.onEvent(SettingEvent.ImportSampleData)
                        }
                    ) {
                        Text(text = stringResource(R.string.import_sample_data))
                    }
                }
                Button(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp,
                        )
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    enabled = !settingsState.isLoadingSampleData,
                    onClick = {
                        viewModel.onEvent(SettingEvent.DeleteAllData)
                    }
                ) {
                    Text(text = stringResource(R.string.delete_all_data))
                }
            }
        },
    )
}

@Composable
private fun SettingSwitch(
    currentLocale: String,
    settingName: Int,
    onCheckedChange: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp),
    ) {
        Text(text = stringResource(id = settingName))
        Text(
            text = stringResource(R.string.spanish),
            modifier = Modifier.padding(horizontal = 8.dp),
        )
        Switch(
            checked = currentLocale == SettingLocale.US.name,
            onCheckedChange = { isChecked ->
                 if (isChecked) {
                     onCheckedChange(SettingLocale.US.name)
                 } else {
                     onCheckedChange(SettingLocale.ES.name)
                 }
            },
        )
        Text(
            text = stringResource(R.string.english),
            modifier = Modifier.padding(start = 8.dp),
        )
    }
}