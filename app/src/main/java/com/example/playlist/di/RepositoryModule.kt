package com.example.playlist.di

import com.example.playlist.data.repository.PlaylistRepository
import org.koin.core.module.Module
import org.koin.dsl.module


val repositoryModule : Module = module {

    single { PlaylistRepository(get()) }

}