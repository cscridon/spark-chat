package com.csc.sparkchat.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.csc.sparkchat.core.common.installSplashScreenAnimation
import com.csc.sparkchat.core.common.setSystemBarAppearance
import com.csc.sparkchat.core.designsystem.theme.SparkChatTheme
import com.csc.sparkchat.presentation.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreenAnimation()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setSystemBarAppearance()
        setContent {
            SparkChatTheme() {
                AppNavHost()
            }
        }
    }
}