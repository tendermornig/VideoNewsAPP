package com.example.videonews.ui.welcome

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.example.videonews.databinding.ActivityWelcomeBinding
import com.example.videonews.ui.NavigationActivity
import com.example.videonews.ui.base.BaseActivity
import com.example.videonews.ui.user.LoginActivity
import com.example.videonews.ui.user.RegisterActivity

/**
 * @author Miracle
 * 欢迎界面
 */
class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(WelcomeViewModel::class.java)
    }

    override fun initViewBinding() = ActivityWelcomeBinding.inflate(layoutInflater)

    override fun initView() {}

    override fun initData() {
        if (viewModel.getUserToken().isNotEmpty()) {
            NavigationActivity.start(this)
        }
        mBinding.btnToLogin.setOnClickListener {
            LoginActivity.start(this)
        }
        mBinding.btnToRegister.setOnClickListener {
            RegisterActivity.start(this)
        }
    }

    companion object {

        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, WelcomeActivity::class.java)
            context.startActivity(starter)
        }
    }
}