package me.sgayazov.flickrdemo.networking

import io.reactivex.Observable
import me.sgayazov.flickrdemo.domain.PhotosWrapper
import me.sgayazov.flickrdemo.domain.TagsWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("/services/rest/?method=flickr.tags.getHotList")
    fun getTopTagsList(@Query("count") count: Int): Observable<TagsWrapper>

    @GET("/services/rest/?method=flickr.photos.search&extras=url_m")
    fun searchForPhotos(@Query("tags") tags: String): Observable<PhotosWrapper>
}