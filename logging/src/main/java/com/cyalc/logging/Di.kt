package com.cyalc.logging

import org.koin.dsl.module

val loggingModule = module {
    single<Logger> { TimberLogger() }
}