package com.example.videonews.ui.base

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.videonews.R
import java.lang.NullPointerException

abstract class BaseFragment : Fragment(), RequestLifecycle, BaseInit {

    /**
     * Fragment中inflate出来的布局。
     */
    private var rootView: View? = null

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

    protected open fun isHaveHeadMargin(): Boolean {
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val frameLayout = FrameLayout(context!!)
        val lce = View.inflate(context, R.layout.layout_lce, null)
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        params.setMargins(
            0,
            if (isHaveHeadMargin()) {
                70
            } else 0,
            0,
            0
        )
        lce.layoutParams = params
        val content = inflater.inflate(getLayoutId(), container, false)
        frameLayout.addView(content)
        frameLayout.addView(lce)
        onCreateView(lce)
        return view
    }

    /**
     * 在Fragment基类中获取通用的控件，会将传入的View实例原封不动返回。
     * @param view
     * Fragment中inflate出来的View实例。
     * @return  Fragment中inflate出来的View实例原封不动返回。
     */
    private fun onCreateView(view: View): View {
        rootView = view
        loadErrorView = view.findViewById(R.id.loadErrorView)
        badNetworkView = view.findViewById(R.id.badNetworkView)
        noContentView = view.findViewById(R.id.noContentView)
        if (loadErrorView == null) {
            throw NullPointerException("loadErrorView is null")
        }
        if (badNetworkView == null) {
            throw NullPointerException("badNetworkView is null")
        }
        if (noContentView == null) {
            throw NullPointerException("noContentView is null")
        }
        return view
    }

    protected open fun setupViews() {
//        loading = findViewById(R.id.loading)
//        loadErrorView = findViewById(R.id.loadErrorView)
//        badNetworkView = findViewById(R.id.badNetworkView)
//        noContentView = findViewById(R.id.noContentView)
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
     * 将load error view进行隐藏。
     * 将bad network view进行隐藏。
     * 将no content view进行隐藏。
     */
    private fun hideLce() {
        loadErrorView?.visibility = View.GONE
        badNetworkView?.visibility = View.GONE
        noContentView?.visibility = View.GONE
    }


    @CallSuper /*@CallSuper 注解：表示任何重写方法都应该调用此方法*/
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

    companion object {
        private const val TAG = "BaseFragment"
    }
}