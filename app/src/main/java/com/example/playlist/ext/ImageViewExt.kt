package com.example.playlist.ext

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.playlist.R
import java.io.File

fun ImageView.loadImage(url: String) {
    Glide.with(this.context)
        .load(url)
        .apply(
            RequestOptions()
                .placeholder(R.drawable.icon_music)
        ).into(this)
}