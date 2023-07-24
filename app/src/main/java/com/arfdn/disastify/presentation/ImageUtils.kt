package com.arfdn.disastify.presentation

import android.content.Context
import android.widget.ImageView
import com.arfdn.disastify.R
import com.bumptech.glide.Glide

fun ImageView.loadImage( context: Context,url: String){
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.baseline_camera_alt_24)
        .into(this)
}