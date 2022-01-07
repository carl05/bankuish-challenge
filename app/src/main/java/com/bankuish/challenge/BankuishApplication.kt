package com.bankuish.challenge

import android.app.Application
import com.bankuish.challenge.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BankuishApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        // start Koin context
        startKoin {
            androidContext(this@BankuishApplication)
//            androidLogger()
            modules(appModule)
        }
    }
}