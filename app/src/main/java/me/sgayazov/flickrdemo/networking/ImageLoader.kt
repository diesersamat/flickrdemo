package me.sgayazov.flickrdemo.networking

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso
import me.sgayazov.flickrdemo.R

class ImageLoader {
    companion object {
        fun loadImage(context: Context, url: String, imageView: ImageView) {
            Picasso.with(context).load(url)
                    .placeholder(R.drawable.image_placeholder)
                    .into(imageView)
        }
    }
}