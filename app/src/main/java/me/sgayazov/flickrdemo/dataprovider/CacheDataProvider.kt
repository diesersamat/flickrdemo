package me.sgayazov.flickrdemo.dataprovider

import io.reactivex.Observable
import me.sgayazov.flickrdemo.domain.PhotosWrapper
import me.sgayazov.flickrdemo.domain.TagsWrapper

//        TODO("not implemented") //implement database provider

class CacheDataProvider {

    fun getTopTagsList(): Observable<TagsWrapper> = Observable.empty()

    fun searchForPhotos(query: String): Observable<PhotosWrapper> = Observable.empty()

    fun savePhotosList(it: PhotosWrapper, query: String) {
    }

    fun saveTopTagsList(it: TagsWrapper) {
    }
}