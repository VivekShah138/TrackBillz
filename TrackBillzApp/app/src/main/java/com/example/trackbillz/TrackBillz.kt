package com.example.trackbillz

import android.app.Application
import com.example.trackbillz.add_bills.di.addBillsModule
import com.example.trackbillz.core.di.coreModule
import com.example.trackbillz.settings.di.settingsModule
import com.example.trackbillz.auth_feature.google_sign.di.googleSignInModule
import com.example.trackbillz.auth_feature.user_pin_setup.di.userPinSetUpModule
import com.example.trackbillz.splash_screen.di.splashScreenModule
import com.example.trackbillz.traders.di.tradersModule
import com.example.trackbillz.view_bills.di.viewBillsModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin

class TrackBillz:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TrackBillz)
            workManagerFactory()
            modules(
                tradersModule,
                addBillsModule,
                viewBillsModule,
                coreModule,
                settingsModule,
                googleSignInModule,
                userPinSetUpModule,
                splashScreenModule
            )
        }
    }
}