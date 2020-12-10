package com.example.videonews.logic.model

import java.io.Serializable

data class VideoModel(
    var vtitle: String,
    var author: String,
    var coverUrl: String,
    var headurl: String,
    var commentNum: Int,
    var collectNum: Int,
    var likeNum: Int,
    var playUrl: String
) : Serializable