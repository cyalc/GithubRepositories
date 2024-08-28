package com.cyalc.connectivity

import org.koin.dsl.module

val connectivityModule = module {
    single<ConnectivityObserver> { ConnectivityObserverImpl(get()) }
}