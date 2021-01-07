package com.example.videonews.ui.base

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import com.dueeeke.videoplayer.player.VideoViewManager
import com.example.videonews.R
import com.example.videonews.utils.showToast
import java.lang.ref.WeakReference

/**
 * @author Miracle
 * 应用中activity的基类
 */
abstract class BaseActivity<T : ViewBinding> : AppCompatActivity(), BaseInit<T> {

    /**
     * 活动对应的ViewBinding
     */
    protected lateinit var mBinding: T

    /**
     * 活动对应的弱引用
     */
    protected var weakReference: WeakReference<Activity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = initViewBinding()
        setContentView(mBinding.root)
        transparentStatusBar()
        weakReference = WeakReference(this)
        ActivityCollector.add(weakReference)
        initView()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.remove(weakReference)
    }

    /**
     * 设置 LiveData 的状态，根据不同状态显示不同页面
     *
     * @param dataLiveData LiveData
     * @param onRequestFinish 访问完成时的回调
     */
    fun <T> setDataStatus(
        dataLiveData: LiveData<Result<T>>,
        onRequestFinish: (T?) -> Unit
    ) {
        dataLiveData.observe(this) {
            if (it.isSuccess) {
                val dataList = it.getOrNull()
                onRequestFinish(dataList)
            } else {
                getString(R.string.bad_network_view_tip).showToast()
                it.exceptionOrNull()?.printStackTrace()
            }
        }
    }

    /**
     * 将状态栏设置成透明。只适配Android 5.0以上系统的手机。
     */
    private fun transparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            //api大于30时使用此方法让布局内容显示到状态栏后
            window.setDecorFitsSystemWindows(false)
//            //解决当布局可在系统状态栏和导航栏后绘制时控件或布局被遮挡的问题
//            window.decorView.setOnApplyWindowInsetsListener { v, insets ->
//                v.updatePadding(bottom = insets.getInsets(WindowInsets.Type.systemBars()).bottom)
//                insets
//            }
        } else {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }

    /**
     * 子类可通过此方法直接拿到VideoViewManager
     */
    protected fun getVideoViewManager() = VideoViewManager.instance()!!
}