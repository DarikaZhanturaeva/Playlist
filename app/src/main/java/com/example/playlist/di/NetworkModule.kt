package com.example.playlist.di

import android.content.Context
import androidx.room.Room
import com.example.playlist.data.local.dao.PlaylistDao
import com.example.playlist.data.local.database.AppDatabase
import org.koin.dsl.module

val networkModule = module {

    single { provideDao(get()) }
    single { provideDatabase(get()) }

}

fun provideDao(database: AppDatabase): PlaylistDao {
    return database.playlistDao()
}

fun provideDatabase(context: Context) = Room.databaseBuilder(
    context,
    AppDatabase::class.java,
    "home_data"
).allowMainThreadQueries().build()