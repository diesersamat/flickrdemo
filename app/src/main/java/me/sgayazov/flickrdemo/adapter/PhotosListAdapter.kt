package me.sgayazov.flickrdemo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import me.sgayazov.flickrdemo.R
import me.sgayazov.flickrdemo.domain.Photo
import me.sgayazov.flickrdemo.networking.ImageLoader

class PhotosListAdapter(private val items: List<Photo>,
                        private val callback: (Photo) -> Unit) : RecyclerView.Adapter<PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
            PhotoViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.photo_item, parent, false))


    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = items[position]
        ImageLoader.loadImage(holder.image.context, photo.url_m, holder.image)
        holder.itemView.setOnClickListener { callback(photo) }
    }

    override fun getItemCount() = items.size
}

class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val image: ImageView = view.findViewById(R.id.image) as ImageView
}