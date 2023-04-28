package com.plcoding.cryptocurrencyappyt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/**
 * to give dagger hill
 * the information about the application
 * so it has access to the application context
 * if it's needed for dependencies
 */

@HiltAndroidApp
class CoinApplication: Application() {
}