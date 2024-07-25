package com.example.playlist.ui.interfaces

import com.example.playlist.data.local.entity.PlaylistModel

interface OnItemClick {
    fun onItemClick(item: PlaylistModel)
    fun onLongItemClick(item: PlaylistModel)
}