package me.sgayazov.flickrdemo.dataprovider

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import me.sgayazov.flickrdemo.domain.PhotosWrapper
import me.sgayazov.flickrdemo.domain.TagsWrapper

private const val MAX_CATEGORIES_COUNT = 5

class Interactor {

    private var cacheDataProvider: CacheDataProvider = CacheDataProvider()
    private var networkDataProvider: NetworkDataProvider = NetworkDataProvider()

    fun searchForListOfPhotos(): Single<HashMap<String, PhotosWrapper>> {
        val hashMap = HashMap<String, PhotosWrapper>()
        return getTopTagsList()
                .flatMapObservable { tagsWrapper ->
                    tagsWrapper.hottags.tag.toObservable()
                }
                .flatMapSingle { tag ->
                    searchForPhotos(tag.content)
                            .map { photosWrapper ->
                                Pair(tag.content, photosWrapper)
                            }
                }
                .collectInto(hashMap,
                        { hash, value ->
                            hash[value.first] = value.second
                        })
    }

    //todo maybe support paging for photos later
    private fun searchForPhotos(query: String): Single<PhotosWrapper> {
        return Observable
                .concat(cacheDataProvider.searchForPhotos(query),
                        networkDataProvider.searchForPhotos(query)
                                .doOnNext({ cacheDataProvider.savePhotosList(it, query) }))
                .firstOrError()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getTopTagsList(): Single<TagsWrapper> {
        return Observable
                .concat(cacheDataProvider.getTopTagsList(),
                        networkDataProvider.getTopTagsList(MAX_CATEGORIES_COUNT)
                                .doOnNext({ cacheDataProvider.saveTopTagsList(it) }))
                .firstOrError()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}