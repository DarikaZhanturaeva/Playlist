package com.example.playlist.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist")
data class PlaylistModel (
    @PrimaryKey
    val id: Int,
    val name: String,
    val author: String,
    val duration: Int,
    val data: String
)