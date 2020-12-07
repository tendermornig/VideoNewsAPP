package com.example.videonews.utils

import android.widget.Toast
import com.example.videonews.VideoNewsApplication

fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(VideoNewsApplication.mContext, this, duration).show()
}

fun Int.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(VideoNewsApplication.mContext, this, duration).show()
}