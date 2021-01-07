package com.example.videonews.utils

import android.widget.Toast
import com.example.videonews.VideoNewsApplication

/**
 * 弹窗简化实体类
 */
fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(VideoNewsApplication.mContext, this, duration).show()
}

fun Int.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(VideoNewsApplication.mContext, this, duration).show()
}