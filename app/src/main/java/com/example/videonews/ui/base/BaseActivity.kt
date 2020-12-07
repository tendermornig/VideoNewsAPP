package com.example.videonews.ui.base

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import com.example.videonews.R
import com.example.videonews.utils.showToast
import java.lang.ref.WeakReference

/**
 * 应用中activity的基类
 */
abstract class BaseActivity : AppCompatActivity(), RequestLifecycle, BaseInit {

    /**
     * Activity中显示加载等待的控件。
     */
    private var loading: ProgressBar? = null

    /**
     * Activity中由于服务器异常导致加载失败显示的布局。
     */
    private var loadErrorView: ConstraintLayout? = null

    /**
     * Activity中由于网络异常导致加载失败显示的布局。
     */
    private var badNetworkView: ConstraintLayout? = null

    /**
     * Activity中当界面上没有任何内容时展示的布局。
     */
    private var noContentView: ConstraintLayout? = null

    private var weakReference: WeakReference<Activity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transparentStatusBar()
        setContentView(getLayoutId())
        weakReference = WeakReference(this)
        ActivityCollector.add(weakReference)
        initView()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.remove(weakReference)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setupViews()
    }

    protected open fun setupViews() {
        val view = View.inflate(this, R.layout.layout_lce, null)
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        params.setMargins(
            0,
            70,
            0,
            0
        )
        addContentView(view, params)
        loading = findViewById(R.id.loading)
        loadErrorView = findViewById(R.id.loadErrorView)
        badNetworkView = findViewById(R.id.badNetworkView)
        noContentView = findViewById(R.id.noContentView)
        if (loading == null) {
            Log.e(TAG, "loading is null")
        }
        if (loadErrorView == null) {
            Log.e(TAG, "loadErrorView is null")
        }
        if (badNetworkView == null) {
            Log.e(TAG, "badNetworkView is null")
        }
    }

    /**
     * 当Activity中的加载内容服务器返回失败，通过此方法显示提示界面给用户。
     *
     * @param tip
     * 界面中的提示信息
     */
    protected fun showLoadErrorView(tip: String = "加载数据失败") {
        loadFailed(tip)
        loadErrorView?.let {
            val loadErrorText = it.findViewById<TextView>(R.id.tvLoadError)
            loadErrorText?.text = tip
            it.visibility = View.VISIBLE
            return
        }
    }

    /**
     * 当Activity中的内容因为网络原因无法显示的时候，通过此方法显示提示界面给用户。
     *
     * @param listener
     * 重新加载点击事件回调
     */
    protected fun showBadNetworkView(listener: View.OnClickListener) {
        loadFinished()
        badNetworkView?.let {
            it.visibility = View.VISIBLE
            it.setOnClickListener(listener)
            return
        }
    }

    /**
     * 当Activity中没有任何内容的时候，通过此方法显示提示界面给用户。
     * @param tip
     * 界面中的提示信息
     */
    protected fun showNoContentView(tip: String) {
        loadFinished()
        noContentView?.let {
            val noContentText = it.findViewById<TextView>(R.id.tvNoContent)
            noContentText?.text = tip
            it.visibility = View.VISIBLE
        }
    }

    /**
     * 设置 LiveData 的状态，根据不同状态显示不同页面
     *
     * @param dataLiveData LiveData
     * @param onDataStatus 数据回调进行使用
     */
    fun <T> setDataStatus(dataLiveData: LiveData<Result<T>>, onDataStatus: (T) -> Unit) {
        dataLiveData.observe(this) {
            if (it.isSuccess) {
                val dataList = it.getOrNull()
                if (dataList != null) {
                    loadFinished()
                    onDataStatus(dataList)
                } else showLoadErrorView()
            } else {
                getString(R.string.bad_network_view_tip).showToast()
                showBadNetworkView { initData() }
            }
        }
    }

    /**
     * 将load error view进行隐藏。
     * 将bad network view进行隐藏。
     * 将no content view进行隐藏。
     */
    private fun hideLce() {
        loadErrorView?.visibility = View.GONE
        badNetworkView?.visibility = View.GONE
        noContentView?.visibility = View.GONE
    }

    @CallSuper
    /*@CallSuper 注解：表示任何重写方法都应该调用此方法*/
    override fun startLoading() {
        hideLce()
        loading?.visibility = View.VISIBLE
    }

    @CallSuper
    override fun loadFinished() {
        loading?.visibility = View.GONE
        hideLce()
    }

    @CallSuper
    override fun loadFailed(msg: String?) {
        loading?.visibility = View.GONE
        hideLce()
    }

    /**
     * 将状态栏设置成透明。只适配Android 5.0以上系统的手机。
     */
    private fun transparentStatusBar() {
        val decorView = window.decorView
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
    }

    companion object {
        private const val TAG = "BaseActivity"
    }
}