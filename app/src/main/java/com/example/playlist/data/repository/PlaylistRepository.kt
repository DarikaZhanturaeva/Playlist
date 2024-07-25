package com.example.playlist.data.repository

import android.util.Log
import com.example.playlist.data.local.dao.PlaylistDao
import com.example.playlist.data.local.entity.PlaylistModel

class PlaylistRepository(private val playlistDao: PlaylistDao) {

    suspend fun delete(playlist: PlaylistModel) = playlistDao.delete(playlist)

}