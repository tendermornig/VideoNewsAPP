package com.example.videonews.base

/**
 * @author Miracle
 * 所有实体类的基类
 */
data class BaseResponse<T>(val msg: String, val code: Int, val data: T)
