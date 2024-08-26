package com.cyalc.logging

import timber.log.Timber

internal class TimberLogger : Logger {
    override fun d(message: String) = Timber.d(message)

    override fun i(message: String) = Timber.i(message)

    override fun e(message: String, throwable: Throwable?) = Timber.e(message, throwable)

    override fun e(throwable: Throwable) = Timber.e(throwable)
}