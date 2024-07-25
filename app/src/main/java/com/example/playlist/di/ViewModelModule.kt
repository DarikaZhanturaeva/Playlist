package com.example.playlist.di

import com.example.playlist.ui.fragment.playlist.PlaylistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelModule : Module = module {

    viewModel { PlaylistViewModel(get()) }
}