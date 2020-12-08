package com.example.videonews.ui.user

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.videonews.R
import com.example.videonews.ui.base.BaseActivity
import com.example.videonews.utils.encoderByMd5
import com.example.videonews.utils.showToast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(LoginViewModel::class.java)
    }

    override fun getLayoutId() = R.layout.activity_login

    override fun initView() {
        btnLogin.setOnClickListener {
            val userAccount = etAccount.text.toString()
            val userPwd = etPwd.text.toString()
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
                "登录成功".showToast()
            } else getString(R.string.login_error_text).showToast()
        }
    }

    private fun verificationLoginParam(
        userAccount: String,
        userPwd: String
    ): Boolean {
        if ("" == userAccount) {
            "账号不可为空".showToast()
            return false
        }
        if (userAccount.length != 10) {
            "账号长度应为10位".showToast()
            return false
        }
        if ("" == userPwd) {
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

        private const val TAG = "LoginActivity"

        fun startLoginActivity(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}