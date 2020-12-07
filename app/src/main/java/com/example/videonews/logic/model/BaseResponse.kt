package com.example.videonews.logic.model

data class BaseResponse<T>(val msg: String, val code: Int, val data: T)
