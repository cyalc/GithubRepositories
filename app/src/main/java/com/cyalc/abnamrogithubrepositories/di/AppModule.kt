package com.cyalc.abnamrogithubrepositories.di

import com.cyalc.repositories.di.featureRepositoriesModule
import org.koin.dsl.module

val appModule = module {
    includes(featureRepositoriesModule)
}
