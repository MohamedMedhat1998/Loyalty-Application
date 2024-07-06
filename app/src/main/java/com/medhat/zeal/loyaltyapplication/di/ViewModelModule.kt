package com.medhat.zeal.loyaltyapplication.di

import com.medhat.zeal.loyaltyapplication.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(get())
    }
}