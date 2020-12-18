package com.example.videonews.ui.base

import android.app.Activity
import android.util.Log
import java.lang.ref.WeakReference

object ActivityCollector {

    private const val TAG = "ActivityCollector"

    private val activityList = ArrayList<WeakReference<Activity>?>()

    fun add(weakReference: WeakReference<Activity>?) {
        activityList.add(weakReference)
    }

    fun remove(weakReference: WeakReference<Activity>?) {
        val result = activityList.remove(weakReference)
        Log.d(TAG, "remove activity reference $result")
    }

    fun finishAll() {
        if (activityList.isNotEmpty()) {
            activityList.forEach {
                val activity = it?.get()
                activity?.finish()
            }
            activityList.clear()
        }
    }
}