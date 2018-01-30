package me.sgayazov.flickrdemo.domain

import com.google.gson.annotations.SerializedName


class TagsWrapper(
        val hottags: TagList
)

class TagList(
        val tag: List<Tag>
)

//
//@Entity
class Tag(
//        @PrimaryKey
        @SerializedName("_content") val content: String
)