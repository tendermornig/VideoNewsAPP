package com.example.videonews.logic.model

import java.io.Serializable

/**
 * @author Miracle
 * 新闻实体类
 */
data class News(
    val newsId: Int,
    val newsTitle: String,
    val authorName: String,
    val headerUrl: String,
    val commentCount: Int,
    val releaseDate: String,
    val type: Int
) : Serializable

/**
 * 新闻略缩图实体类
 */
data class Thumb(val thumbId: Int, val thumbUrl: String, val newsId: Int) : Serializable