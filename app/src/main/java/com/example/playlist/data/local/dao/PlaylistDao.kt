package com.example.playlist.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.playlist.data.local.entity.PlaylistModel

@Dao
interface PlaylistDao {

    @Query("SELECT * FROM playlist")
    fun getAll(): LiveData<List<PlaylistModel>>

    @Query("SELECT * FROM playlist WHERE id = :id")
    fun getById(id: Int): PlaylistModel

    @Update
    suspend fun update(playlist: PlaylistModel)

    @Insert
    suspend fun insert(playlist: PlaylistModel)

    @Delete
    suspend fun delete(playlist: PlaylistModel)

}