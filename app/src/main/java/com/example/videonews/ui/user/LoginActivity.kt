package com.example.videonews.ui.user

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.example.videonews.R
import com.example.videonews.databinding.ActivityLoginBinding
import com.example.videonews.ui.NavigationActivity
import com.example.videonews.ui.base.BaseActivity
import com.example.videonews.utils.encoderByMd5
import com.example.videonews.utils.showToast

/**
 * @author Miracle
 * 登录界面
 */
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(LoginViewModel::class.java)
    }

    override fun initViewBinding() = ActivityLoginBinding.inflate(layoutInflater)

    override fun initView() {
        mBinding.btnLogin.setOnClickListener {
            val userAccount = mBinding.etAccount.text.toString()
            val userPwd = mBinding.etPwd.text.toString()
            if (verificationLoginParam(userAccount, userPwd)) {
                val encryptionUserPwd = encoderByMd5(userPwd)
                val loginParam =
                    mapOf("userAccount" to userAccount, "userPassword" to encryptionUserPwd)
                viewModel.setRequestValue(loginParam)
            }
        }
    }

    override fun initData() {
        setDataStatus(viewModel.dataLiveData) {
            if (it != null && "" != it) {
                viewModel.saveUserToken(it)
                NavigationActivity.start(this)
                "登录成功".showToast()
            } else getString(R.string.login_error_tip).showToast()
        }
    }

    /**
     * 验证用户登录参数是否合法函数
     * @param userAccount 用户账户
     * @param userPwd 用户密码
     * @return 验证结果
     */
    private fun verificationLoginParam(
        userAccount: String,
        userPwd: String
    ): Boolean {
        if (userAccount.isEmpty()) {
            "账号不可为空".showToast()
            return false
        }
        if (userAccount.length != 10) {
            "账号长度应为10位".showToast()
            return false
        }
        if (userPwd.isEmpty()) {
            "密码不可为空".showToast()
            return false
        }
        if (userPwd.length < 9) {
            "密码长度应大于或等于10位".showToast()
            return false
        }
        return true
    }

    companion object {

        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, LoginActivity::class.java)
            context.startActivity(starter)
        }
    }
}