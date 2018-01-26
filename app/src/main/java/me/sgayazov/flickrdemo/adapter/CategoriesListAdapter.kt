package me.sgayazov.flickrdemo.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.sgayazov.flickrdemo.R
import me.sgayazov.flickrdemo.domain.Photo
import me.sgayazov.flickrdemo.domain.PhotosWrapper

private const val ITEM_HEADER = 1
private const val ITEM_LIST = 2

class CategoriesListAdapter(private val items: List<Any>,
                            private val callback: (Photo) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        ITEM_HEADER -> HeaderViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.header_photos_item, parent, false))
        ITEM_LIST -> ListViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.list_photos_item, parent, false))
        else -> {
            throw IllegalStateException("Wrong view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM_HEADER -> (holder as HeaderViewHolder).title.text = items[position] as String
            ITEM_LIST -> (holder as ListViewHolder).setPhoto(items[position] as PhotosWrapper, callback)
            else -> {
                throw IllegalStateException("Wrong view type")
            }
        }
    }

    override fun getItemViewType(position: Int): Int = when {
        items[position] is PhotosWrapper -> ITEM_LIST
        items[position] is String -> ITEM_HEADER
        else -> super.getItemViewType(position)
    }

    override fun getItemCount() = items.size
}

class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title: TextView = view as TextView
}

class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val list: RecyclerView = view as RecyclerView

    fun setPhoto(photosList: PhotosWrapper, callback: (Photo) -> Unit) {
        list.layoutManager = LinearLayoutManager(list.context, LinearLayoutManager.HORIZONTAL, false)
        list.adapter = PhotosListAdapter(photosList.photos.photo, callback)
    }
}