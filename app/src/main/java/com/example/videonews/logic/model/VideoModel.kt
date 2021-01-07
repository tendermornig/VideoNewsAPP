package com.example.videonews.logic.model

import java.io.Serializable

/**
 * @author Miracle
 * 视频实体类
 */
data class Video(
    var vtitle: String,
    var author: String,
    var coverUrl: String,
    var headurl: String,
    var commentNum: Int,
    var collectNum: Int,
    var likeNum: Int,
    var playUrl: String
) : Serializable

/**
 * 视频分类实体类
 */
data class Category(var categoryId: Int, var categoryName: String) : Serializable