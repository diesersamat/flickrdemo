package me.sgayazov.flickrdemo.activity

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.activity_photos_list.*
import kotlinx.android.synthetic.main.loading_layout.*
import me.sgayazov.flickrdemo.R
import me.sgayazov.flickrdemo.adapter.CategoriesListAdapter
import me.sgayazov.flickrdemo.domain.Photo
import me.sgayazov.flickrdemo.presenter.PhotosListPresenter

const val PHOTO_LLM_EXTRA = "photo_llm_extra"
const val PHOTO_EXTRA = "photo_extra"

class PhotosListActivity : AppCompatActivity(), PhotosListView {

    private val presenter = PhotosListPresenter(this)
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos_list)
        layoutManager = photo_categories_list.layoutManager
    }

    override fun startPhotoListLoad() {
        error_view.visibility = View.GONE
        error_retry_button.visibility = View.VISIBLE
        photo_categories_list.visibility = View.GONE
        loading_view.visibility = View.VISIBLE
    }

    override fun onError(error: Throwable) {
        loading_view.visibility = View.GONE
        error_view.visibility = View.VISIBLE
        error_reason.text = error.localizedMessage
        error_retry_button.setOnClickListener { presenter.loadData() }
    }

    override fun onDataReceived(data: List<Any>) {
        photo_categories_list.adapter = CategoriesListAdapter(data, { openPhotoDetailed(it) })
        loading_view.visibility = View.GONE
        error_view.visibility = View.GONE
        photo_categories_list.visibility = View.VISIBLE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(PHOTO_LLM_EXTRA, photo_categories_list.layoutManager.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.let {
            photo_categories_list.layoutManager.onRestoreInstanceState(it.getParcelable<Parcelable>(PHOTO_LLM_EXTRA))
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.loadData()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    private fun openPhotoDetailed(photo: Photo) {
        val intent = Intent(this, PhotoDetailActivity::class.java).apply {
            putExtra(PHOTO_EXTRA, photo)
        }
        startActivity(intent)
    }
}

interface PhotosListView {
    fun onDataReceived(data: List<Any>)
    fun onError(error: Throwable)
    fun startPhotoListLoad()
}