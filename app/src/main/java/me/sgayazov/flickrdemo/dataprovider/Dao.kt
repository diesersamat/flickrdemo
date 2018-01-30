package me.sgayazov.flickrdemo.dataprovider

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import me.sgayazov.flickrdemo.domain.Photo
import me.sgayazov.flickrdemo.domain.Tag


@Dao
interface PhotosDao {
    @Query("SELECT * FROM photo")
    fun all(): List<Photo>

    @Query("SELECT * FROM photo WHERE tags LIKE :tag")
    fun findByTag(tag: String): List<Photo>

    @Insert
    fun insertAll(vararg users: Photo)

    @Delete
    fun delete(user: Photo)
}

@Dao
interface TagsDao {
    @Query("SELECT * FROM tag")
    fun all(): List<Tag>

    @Insert
    fun insertAll(vararg tags: Tag)

    @Delete
    fun delete(tag: Tag)
}