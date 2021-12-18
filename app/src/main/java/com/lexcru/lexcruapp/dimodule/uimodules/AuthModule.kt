package com.lexcru.lexcruapp.dimodule.uimodules

import com.lexcru.lexcruapp.viewmodels.AuthViewModel
import com.lexcru.lexcruapp.viewmodels.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { AuthViewModel(get(),get()) }
}