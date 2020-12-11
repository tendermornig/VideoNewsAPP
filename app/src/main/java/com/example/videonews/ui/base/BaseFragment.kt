package com.example.videonews.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.videonews.R
import com.example.videonews.ui.welcome.WelcomeActivity
import com.example.videonews.utils.showToast

abstract class BaseFragment : Fragment(), BaseInit {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
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
        WelcomeActivity.startWelcomeActivity(context!!)
        ActivityCollector.finishAll()
        msg.showToast()
    }

    companion object {
        private const val TAG = "BaseFragment"
    }
}