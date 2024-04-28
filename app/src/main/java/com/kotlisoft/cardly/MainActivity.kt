package com.kotlisoft.cardly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kotlisoft.cardly.ui.theme.CardlyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            CardlyTheme {
                NavHost(
                    navController = navController,
                    startDestination = "topics",
                ) {
                    composable(route = "topics") {

                    }
                }
            }
        }
    }
}