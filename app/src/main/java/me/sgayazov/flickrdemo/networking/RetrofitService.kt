package me.sgayazov.flickrdemo.networking

import io.reactivex.Observable
import io.reactivex.Single
import me.sgayazov.flickrdemo.domain.PhotosWrapper
import me.sgayazov.flickrdemo.domain.TagsWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("/services/rest/?method=flickr.tags.getHotList")
    fun getTopTagsList(@Query("count") count: Int): Single<TagsWrapper>

    @GET("/services/rest/?method=flickr.photos.search&extras=url_m,url_c,tags")
    fun searchForPhotos(@Query("tags") tags: String): Single<PhotosWrapper>
}