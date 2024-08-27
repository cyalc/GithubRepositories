package com.cyalc.logging

interface Logger {
    fun d(message: String)
    fun i(message: String)
    fun e(message: String, throwable: Throwable? = null)
    fun e(throwable: Throwable)
    fun initialize()
}