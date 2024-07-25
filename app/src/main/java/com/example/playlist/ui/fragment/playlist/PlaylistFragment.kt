package com.example.playlist.ui.fragment.playlist

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.playlist.App
import com.example.playlist.R
import com.example.playlist.data.local.entity.PlaylistModel
import com.example.playlist.databinding.FragmentPlaylistBinding
import com.example.playlist.ui.base.BaseFragment
import com.example.playlist.ui.interfaces.OnItemClick
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistFragment : BaseFragment<FragmentPlaylistBinding>(FragmentPlaylistBinding::inflate) ,
    OnItemClick {

    private lateinit var adapter: PlaylistAdapter
    private val viewModel: PlaylistViewModel by viewModel()
    private val musicList = mutableListOf<PlaylistModel>()

    override fun setupObservers() {
//        viewModel.getAll().observe(this.viewLifecycleOwner){ playlists ->
//            Log.d("PlaylistFragment", "setupObservers: ${playlists.size}")
//            adapter.submitList(playlists)
//        }

    }

    override fun setupViews() {
        setupRecyclerView()
    }


    private fun setupRecyclerView() {
        adapter = PlaylistAdapter(this)
        binding.rvPlaylist.adapter = adapter
        requestPermission()
        binding.rvPlaylist.post {
            Log.d("PlaylistFragment", "RecyclerView visibility: ${binding.rvPlaylist.visibility}, width: ${binding.rvPlaylist.width}, height: ${binding.rvPlaylist.height}")
        }
    }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                loadMusicFiles()
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_MEDIA_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(Manifest.permission.READ_MEDIA_AUDIO)
        } else {
            loadMusicFiles()
        }
    }

    private fun loadMusicFiles() {
        musicList.clear()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA
        )
        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

        val resolver = this.requireContext().contentResolver
        val cursor = resolver.query(uri, projection, selection, null, sortOrder)
        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val durationColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val dataColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val title = it.getString(titleColumn)
                val artist = it.getString(artistColumn)
                val duration = it.getInt(durationColumn)
                val data = it.getString(dataColumn)

                musicList.add(PlaylistModel(id.toInt(), title, artist, duration, data))
            }
        }

        Log.d("PlaylistFragment2", "Loaded music list size: ${musicList.size}")
        adapter.submitList(musicList)
        Log.d("PlaylistFragment", "Adapter item count after submitList: ${adapter.itemCount}")
    }

    override fun onItemClick(item: PlaylistModel) {
        val action = PlaylistFragmentDirections.actionPlaylistFragmentToPlaylistDetailFragment(
            trackList = musicList.map { it.data }.toTypedArray(), // Передача списка URI треков как Array
            currentTrackIndex = musicList.indexOf(item) // Индекс текущего трека
        )
        findNavController().navigate(action)
    }

    override fun onLongItemClick(item: PlaylistModel) {
        val builder = AlertDialog.Builder(requireContext())
        with(builder) {
            setTitle("Вы точно хотите удалить?")
            setPositiveButton("Да") { dialog, which ->
                viewModel.delete(item)
            }
            setNegativeButton("Нет") { dialog, which ->
                dialog.cancel()
            }
            show()
        }
        builder.create()
    }
}