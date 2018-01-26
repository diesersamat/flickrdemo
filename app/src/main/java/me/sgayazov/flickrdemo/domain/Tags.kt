package me.sgayazov.flickrdemo.domain

import com.google.gson.annotations.SerializedName


class TagsWrapper(
        val hottags: TagList,
        val stat: String
)

class TagList(
        val period: String,
        val count: Int,
        val tag: List<Tag>
)

class Tag(
        @SerializedName("_content") val content: String,
        val score: String
)