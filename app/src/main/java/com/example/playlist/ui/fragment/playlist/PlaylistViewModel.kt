package com.example.playlist.ui.fragment.playlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist.data.local.entity.PlaylistModel
import com.example.playlist.data.repository.PlaylistRepository
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val repository: PlaylistRepository
) :ViewModel() {

    fun delete(playlistModel: PlaylistModel) = viewModelScope.launch {
        Log.d("HotelViewModel", "Deleting hotel: ${playlistModel.name}")
        repository.delete(playlistModel)
    }

}