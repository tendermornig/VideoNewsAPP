package com.example.videonews.ui.base

import android.app.Activity
import android.util.Log
import java.lang.ref.WeakReference

/**
 * @author Miracle
 * 活动收集器用于管理活动
 */
object ActivityCollector {

    /**
     * 日志TAG
     */
    private const val TAG = "ActivityCollector"

    /**
     * 活动弱引用集合
     */
    private val activityList = ArrayList<WeakReference<Activity>?>()

    /**
     * 将活动添加进集合方法
     * @param weakReference 活动弱引用
     */
    fun add(weakReference: WeakReference<Activity>?) {
        activityList.add(weakReference)
    }

    /**
     * 在集合中移除活动方法
     * @param weakReference 活动弱引用
     */
    fun remove(weakReference: WeakReference<Activity>?) {
        val result = activityList.remove(weakReference)
        Log.d(TAG, "remove activity reference $result")
    }

    /**
     * 清空活动栈方法
     */
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