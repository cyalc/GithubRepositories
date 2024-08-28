package com.cyalc.repositories.di

import com.cyalc.repositories.ui.home.RepositoryListViewModel
import com.cyalc.repositories.datasource.local.dataRepositoriesModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureRepositoriesModule = module {
    includes(dataRepositoriesModule)
    viewModel { RepositoryListViewModel(get(), get()) }
}