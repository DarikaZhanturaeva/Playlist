package com.example.playlist.ui.fragment.playlist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.playlist.data.local.entity.PlaylistModel
import com.example.playlist.databinding.ItemPlaylistBinding
import com.example.playlist.ext.loadImage
import com.example.playlist.ui.interfaces.OnItemClick

class PlaylistAdapter(private val onClick: OnItemClick) :
    ListAdapter<PlaylistModel, PlaylistAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPlaylistBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClick.onItemClick(item)
        }
        holder.itemView.setOnLongClickListener {
            onClick.onLongItemClick(getItem(position))
            true
        }
    }


    class ViewHolder(private val binding: ItemPlaylistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(playlistModel: PlaylistModel) {
            binding.apply {
                Log.d("PlaylistAdapter", "bind: $playlistModel")
                tvMusicName.text = playlistModel.name
                tvMusicAuthor.text = playlistModel.author
                tvMusicDuration.text = playlistModel.duration.toString()
                imageView.loadImage(imageView.toString())
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<PlaylistModel>() {
        override fun areItemsTheSame(oldItem: PlaylistModel, newItem: PlaylistModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PlaylistModel, newItem: PlaylistModel): Boolean {
            return oldItem == newItem
        }
    }

}