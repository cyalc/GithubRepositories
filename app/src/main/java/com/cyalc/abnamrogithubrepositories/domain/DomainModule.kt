package com.cyalc.abnamrogithubrepositories.domain

import org.koin.dsl.module

val domainModule = module {
    single {
        FetchAndSaveGithubRepositoryList(get(), get())
    }
}