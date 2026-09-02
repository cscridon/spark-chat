package com.csc.sparkchat.core.common

import android.animation.ObjectAnimator
import android.app.Activity
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.activity.ComponentActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

/**
 * Configures system status bar and navigation bar icon appearance (light or dark).
 * @param isLight true if the background is light
 */
fun Activity.setSystemBarAppearance(isLight: Boolean = true) {
    WindowCompat.getInsetsController(window, window.decorView).apply {
        isAppearanceLightStatusBars = isLight
        isAppearanceLightNavigationBars = isLight
    }
}

/**
 * Installs and configures the AndroidX Core Splashscreen with a custom duration delay
 * and a smooth fade-out exit animation.
 *
 * @param delayMillis The duration in milliseconds to keep the splash screen visible
 *                    before starting the exit transition (defaults to 500ms).
 */
fun ComponentActivity.installSplashScreenAnimation(delayMillis: Long = 400L) {
    var isSplashReady = false
    val splashScreen = installSplashScreen()
    splashScreen.setKeepOnScreenCondition { !isSplashReady }

    lifecycleScope.launch {
        delay(delayMillis.milliseconds)
        isSplashReady = true
    }

    splashScreen.setOnExitAnimationListener { splashScreenView ->
        val fadeOut = ObjectAnimator.ofFloat(
            splashScreenView.view, View.ALPHA, 1f, 0f
        ).apply {
            duration = 300L
            interpolator = AccelerateInterpolator()
            doOnEnd {
                splashScreenView.remove()
            }
        }
        fadeOut.start()
    }
}
