package com.example.videonews.logic.model

/**
 * @author Miracle
 * 响应数据基础实体类
 */
data class BaseResponse<T>(val msg: String, val code: Int, val data: T)
