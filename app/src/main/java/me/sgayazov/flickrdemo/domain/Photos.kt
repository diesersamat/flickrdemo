package me.sgayazov.flickrdemo.domain

import android.os.Parcel
import android.os.Parcelable

class PhotosWrapper(
        val photos: PhotoList,
        val stat: String
)

class PhotoList(
        val page: Int,
        val pages: String,
        val perpage: Int,
        val total: String,
        val photo: List<Photo>
)

class Photo(
        val id: String,
        val title: String,
        val owner: String,
        val url_m: String,
        val url_c: String
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(title)
        writeString(owner)
        writeString(url_m)
        writeString(url_c)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Photo> = object : Parcelable.Creator<Photo> {
            override fun createFromParcel(source: Parcel): Photo = Photo(source)
            override fun newArray(size: Int): Array<Photo?> = arrayOfNulls(size)
        }
    }
}