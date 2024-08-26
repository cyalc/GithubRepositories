package com.cyalc.repositories.di

import org.koin.dsl.module

val featureRepositoriesModule = module {
    dataRepositoriesModule
}