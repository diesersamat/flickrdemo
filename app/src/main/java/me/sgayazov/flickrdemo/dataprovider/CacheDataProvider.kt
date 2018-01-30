package me.sgayazov.flickrdemo.dataprovider

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import io.reactivex.Observable
import me.sgayazov.flickrdemo.domain.Photo
import me.sgayazov.flickrdemo.domain.PhotosWrapper
import me.sgayazov.flickrdemo.domain.Tag
import me.sgayazov.flickrdemo.domain.TagsWrapper


//        TODO("not implemented") //implement database provider

class CacheDataProvider {

//    var db = Room.databaseBuilder(application, AppDatabase::class.java, "photos-database").build()

    fun getTopTagsList(): Observable<TagsWrapper> {
//        val tagsList: MutableList<Tag> = mutableListOf()
//        return db.tagsDao().all().collectInto(tagsList, { list, tag -> list.add(tag) }).ma { tags -> TagsWrapper(TagList(tags)) }
        return Observable.empty()
    }

    fun searchForPhotos(query: String): Observable<PhotosWrapper> = Observable.empty()

    fun savePhotosList(it: PhotosWrapper, query: String) {
    }

    fun saveTopTagsList(it: TagsWrapper) {
    }
}
//
//@Database(entities = [(Photo::class), (Tag::class)], version = 1)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun photosDao(): PhotosDao
//    abstract fun tagsDao(): TagsDao
//}