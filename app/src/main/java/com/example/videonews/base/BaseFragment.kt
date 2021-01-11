package com.example.videonews.base

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
import com.example.videonews.logic.Repository
import com.example.videonews.ui.welcome.WelcomeActivity
import com.example.videonews.utils.showToast

/**
 * @author Miracle
 * 应用中所有Fragment的基类
 */
abstract class BaseFragment<T : ViewBinding> : Fragment(), BaseInit<T> {

    /**
     * fragment对应的上下文
     */
    protected lateinit var mActivity: Activity

    /**
     * fragment对应的ViewBinding
     */
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
    fun <T> setLiveDataStatus(
        dataLiveData: LiveData<Result<BaseResponse<T>>>,
        onRequestFinish: (T) -> Unit
    ) {
        dataLiveData.observe(this) {
            if (it.isSuccess) {
                val result = it.getOrNull()
                if (result != null && result.code != 402) {
                    onRequestFinish(result.data)
                } else {
                    Repository.clearUserToken()
                    toReLogin(getString(R.string.user_login_token_be_overdue_tip))
                }
            } else {
                getString(R.string.bad_network_view_tip).showToast()
                it.exceptionOrNull()?.printStackTrace()
            }
        }
    }

    /**
     * 当用户登录过期时 调用此方法跳转到登录页面重新登录
     * @param msg 提示信息
     */
    fun toReLogin(msg: String) {
        ActivityCollector.finishAll()
        WelcomeActivity.start(mActivity)
        msg.showToast()
    }

    /**
     * 子类可通过此方法直接拿到VideoViewManager
     */
    protected fun getVideoViewManager(): VideoViewManager? {
        return VideoViewManager.instance()
    }
}