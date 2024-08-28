package com.cyalc.repositories.di

import com.cyalc.connectivity.connectivityModule
import com.cyalc.repositories.ui.home.RepoHomeViewModel
import com.cyalc.repositories.datasource.local.dataReposModule
import com.cyalc.repositories.ui.detail.RepoDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureRepositoriesModule = module {
    includes(dataReposModule, connectivityModule)
    viewModel { RepoHomeViewModel(get(), get(), get()) }
    viewModel { RepoDetailViewModel(get()) }
}