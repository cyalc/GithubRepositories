package com.cyalc.abnamrogithubrepositories

import android.app.Application
import com.cyalc.abnamrogithubrepositories.di.appModule
import com.cyalc.logging.Logger
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RepoApplication : Application() {
  private val logger: Logger by inject()

  override fun onCreate() {
    super.onCreate()
    startKoin {
      androidContext(this@RepoApplication)
      androidLogger()
      modules(appModule)
    }

    if (BuildConfig.DEBUG) {
      logger.initialize()
    }
  }
}
