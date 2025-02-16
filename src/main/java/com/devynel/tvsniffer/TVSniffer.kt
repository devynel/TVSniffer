package com.devynel.tvsniffer

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.hardware.Sensor
import android.hardware.SensorManager
import android.util.Log
import androidx.core.content.ContextCompat
import kotlin.math.abs

data class TVSnifferResult(val tvThresholdMet: Boolean, val tvScore: Double, val tvMaybe: Boolean)

object TVSniffer {
    private const val TAG = "TVSniffer"
    private const val DEFAULT_THRESHOLD = 8.0

    fun isTvDevice(
        context: Context,
        tvThresholdNeeded: Double = DEFAULT_THRESHOLD,
        tvUiModeScore: Int = 2,
        noAccelerometerScore: Int = 4,
        leanbackKeyFileScore: Int = 5,
        leanbackLibScore: Int = 5,
        leanbackLauncherScore: Int = 1,
        tvSettingsScore: Int = 2,
        tvRecommendationScore: Int = 1,
        tvPartnerCustomizerScore: Int = 1
    ): TVSnifferResult {
        var deviceTvConfidenceScore = 0.0
        //check the UI mode
        if (isUiModeTv(context)) {
            deviceTvConfidenceScore += tvUiModeScore
        }
        //check for an accelerometer
        if (!hasAccelerometer(context)) {
            deviceTvConfidenceScore += noAccelerometerScore
        }

        //check for leanback keyboard file
        if (hasLeanbackKeyboardFile(context)) {
            deviceTvConfidenceScore += leanbackKeyFileScore
        }

        //check for leanback library
        if (hasLeanbackLibrary(context)) {
            deviceTvConfidenceScore += leanbackLibScore
        }

        //check for tv settings
        if (hasTvSettings(context)) {
            deviceTvConfidenceScore += tvSettingsScore
        }

        //check for leanback launcher
        if (hasLeanbackLauncher(context)) {
            deviceTvConfidenceScore += leanbackLauncherScore
        }

        //check for tv reccomendation service
        if (hasTvRecommendationService(context)) {
            deviceTvConfidenceScore += tvRecommendationScore
        }

        //check for tv partner customizer
        if (hasTvPartnerCustomizer(context)) {
            deviceTvConfidenceScore += tvPartnerCustomizerScore
        }

        val thresholdMet = deviceTvConfidenceScore >= tvThresholdNeeded
        val proximity: Double = 0.5
        val tvMaybeValue = if (!thresholdMet) {
            abs(deviceTvConfidenceScore - tvThresholdNeeded) <= proximity
        } else {
            false
        }
        val result = TVSnifferResult(thresholdMet, deviceTvConfidenceScore, tvMaybeValue)
        return result
    }

    private fun isUiModeTv(context: Context): Boolean {
        val uiMode = context.resources.configuration.uiMode and Configuration.UI_MODE_TYPE_MASK
        val result = uiMode == Configuration.UI_MODE_TYPE_TELEVISION
        Log.d(TAG, "isUiModeTv: $result")
        return result
    }

    private fun hasAccelerometer(context: Context): Boolean {
        val sensorManager = ContextCompat.getSystemService(context, SensorManager::class.java)
        val accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val result = accelerometer != null
        Log.d(TAG, "hasAccelerometer: $result")
        return result
    }

    private fun hasLeanbackKeyboardFile(context: Context): Boolean {
        //This method checks for the existence of a specific keyboard layout file used by Leanback on Android TV devices.
        val result = false
        Log.d(TAG, "hasLeanbackKeyboardFile: $result")
        return result
    }

    private fun hasLeanbackLibrary(context: Context): Boolean {
        val result = try {
            val packageInfo = context.packageManager.getPackageInfo("androidx.leanback", 0)
            packageInfo.versionName != null
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
        Log.d(TAG, "hasLeanbackLibrary: $result")
        return result
    }

    private fun hasTvSettings(context: Context): Boolean {
        //This method checks for the presence of settings typically found on Android TV devices.
        val result = false
        Log.d(TAG, "hasTvSettings: $result")
        return result
    }

    private fun hasLeanbackLauncher(context: Context): Boolean {
        //This method checks if a Leanback launcher is set as the default launcher.
        val result = false
        Log.d(TAG, "hasLeanbackLauncher: $result")
        return result
    }

    private fun hasTvRecommendationService(context: Context): Boolean {
        //This method checks for the presence of a TV recommendation service.
        val result = false
        Log.d(TAG, "hasTvRecommendationService: $result")
        return result
    }

    private fun hasTvPartnerCustomizer(context: Context): Boolean {
        //This method checks for the presence of the TV Partner Customizer package.
        val result = false
        Log.d(TAG, "hasTvPartnerCustomizer: $result")
        return result
    }
}