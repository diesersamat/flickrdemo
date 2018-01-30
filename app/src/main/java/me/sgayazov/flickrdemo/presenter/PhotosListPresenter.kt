package me.sgayazov.flickrdemo.presenter

import android.app.Application
import me.sgayazov.flickrdemo.activity.PhotosListView
import me.sgayazov.flickrdemo.domain.PhotosWrapper

class PhotosListPresenter constructor(private val view: PhotosListView) : BasePresenter() {

    private var data: List<Any>? = null

    fun loadData() {
        view.startPhotoListLoad()
        addSubscription(interactor.searchForListOfPhotos().subscribe({ map: HashMap<String, PhotosWrapper>?, throwable: Throwable? ->
            throwable?.let { view.onError(it) }
            map?.let { onDataReceived(it) }
        }))
    }

    private fun onDataReceived(it: HashMap<String, PhotosWrapper>) {
        val dataList = mutableListOf<Any>()
        it.asIterable().fold(dataList, { list, entry ->
            list.add(entry.key)
            list.add(entry.value)
            return@fold list
        })
        data = dataList
        view.onDataReceived(dataList)
    }
}