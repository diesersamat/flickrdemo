package me.sgayazov.flickrdemo.domain

import android.os.Parcel
import android.os.Parcelable

class PhotosWrapper(
        val photos: PhotoList
)

class PhotoList(
        val photo: List<Photo>
)

//@Entity
class Photo(
//        @PrimaryKey
        val id: String,
//        @ColumnInfo(name = "title")
        val title: String,
//        @ColumnInfo(name = "owner")
        val owner: String,
//        @ColumnInfo(name = "url_m")
        val url_m: String,
//        @ColumnInfo(name = "url_c")
        val url_c: String,
//        @ColumnInfo(name = "tags")
        val tags: String
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
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
        writeString(tags)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Photo> = object : Parcelable.Creator<Photo> {
            override fun createFromParcel(source: Parcel): Photo = Photo(source)
            override fun newArray(size: Int): Array<Photo?> = arrayOfNulls(size)
        }
    }
}