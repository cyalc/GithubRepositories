package com.cyalc.abnamrogithubrepositories

import android.app.Application
import com.cyalc.abnamrogithubrepositories.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class RepoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RepoApplication)
            androidLogger()
            modules(appModule)
        }
    }
}