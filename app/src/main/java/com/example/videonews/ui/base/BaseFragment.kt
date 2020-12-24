package com.example.videonews.ui.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import com.dueeeke.videoplayer.player.VideoViewManager
import com.example.videonews.R
import com.example.videonews.ui.welcome.WelcomeActivity
import com.example.videonews.utils.showToast

abstract class BaseFragment<T : ViewBinding> : Fragment(), BaseInit<T> {

    protected lateinit var mActivity: Activity
    protected lateinit var mBinding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mActivity = activity!!
        mBinding = initViewBinding()
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
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

    fun toReLogin(msg: String) {
        ActivityCollector.finishAll()
        WelcomeActivity.startWelcomeActivity(mActivity)
        msg.showToast()
    }

    /**
     * 子类可通过此方法直接拿到VideoViewManager
     */
    protected fun getVideoViewManager(): VideoViewManager? {
        return VideoViewManager.instance()
    }
}