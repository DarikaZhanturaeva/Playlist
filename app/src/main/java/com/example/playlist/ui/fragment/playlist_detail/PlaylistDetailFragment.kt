package com.example.playlist.ui.fragment.playlist_detail

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.navigation.fragment.findNavController
import com.example.playlist.R
import com.example.playlist.databinding.FragmentPlaylistDetailBinding
import com.example.playlist.ui.base.BaseFragment

class PlaylistDetailFragment :
    BaseFragment<FragmentPlaylistDetailBinding>(FragmentPlaylistDetailBinding::inflate) {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var trackList: Array<String>
    private var currentTrackIndex: Int = 0

    override fun setupViews() {
        getArgument()
    }

    override fun initClickListeners() {
        controlSound()
        initBackButton()
    }


    private fun getArgument() {
        arguments?.let {
            trackList = it.getStringArray("trackList") ?: arrayOf()
            currentTrackIndex = it.getInt("currentTrackIndex")
        }
    }

    private fun controlSound() {
        binding.imgPlay.setOnClickListener {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer().apply {
                    setOnPreparedListener {
                        start()
                        initSeekBar()
                        binding.imgPlay.visibility = View.GONE
                        binding.imgPause.visibility = View.VISIBLE
                    }
                    setDataSource(trackList[currentTrackIndex])
                    prepareAsync()
                }
            } else {
                mediaPlayer?.start()
                binding.imgPlay.visibility = View.GONE
                binding.imgPause.visibility = View.VISIBLE
            }
            Log.d("PlaylistDetailFragment", "controlSound: ${mediaPlayer?.isPlaying}")
        }

        binding.imgPause.setOnClickListener {
            mediaPlayer?.pause()
            binding.imgPlay.visibility = View.VISIBLE
            binding.imgPause.visibility = View.GONE
        }

        binding.imgNext.setOnClickListener {
            if (currentTrackIndex < trackList.size - 1) {
                currentTrackIndex++
                changeTrack(trackList[currentTrackIndex])
            }
        }

        binding.imgPrev.setOnClickListener {
            if (currentTrackIndex > 0) {
                currentTrackIndex--
                changeTrack(trackList[currentTrackIndex])
            }
        }
    }

    private fun initSeekBar() {
        binding.seekBar.setOnSeekBarChangeListener(@SuppressLint("AppCompatCustomView")
        object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mediaPlayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        binding.seekBar.max = mediaPlayer!!.duration
    }


    private fun changeTrack(uri: String) {
        mediaPlayer?.reset()
        mediaPlayer?.apply {
            setOnPreparedListener {
                start()
                binding.imgPlay.visibility = View.GONE
                binding.imgPause.visibility = View.VISIBLE
            }
            setDataSource(uri)
            prepareAsync()
        }
    }

    private fun initBackButton() {
        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}