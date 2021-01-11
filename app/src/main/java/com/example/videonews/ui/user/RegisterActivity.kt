package com.example.videonews.ui.user

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.example.videonews.R
import com.example.videonews.base.ActivityCollector
import com.example.videonews.base.BaseActivity
import com.example.videonews.databinding.ActivityRegisterBinding
import com.example.videonews.utils.Const
import com.example.videonews.utils.encoderByMd5
import com.example.videonews.utils.showToast

/**
 * @author Miracle
 * 注册界面
 */
class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(RegisterViewModel::class.java)
    }

    override fun initViewBinding() = ActivityRegisterBinding.inflate(layoutInflater)

    override fun initView() {
        mBinding.btnRegister.setOnClickListener {
            val userAccount = mBinding.etAccount.text.toString()
            val userPwd = mBinding.etPwd.text.toString()
            val userTwoPwd = mBinding.etTwoPwd.text.toString()
            if (verificationRegisterParam(userAccount, userPwd, userTwoPwd)) {
                val encryptionUserPwd = encoderByMd5(userPwd)
                val registerParam =
                    mapOf(Const.USER_ACCOUNT to userAccount, Const.USER_PASSWORD to encryptionUserPwd)
                viewModel.setRequestValue(registerParam)
            }
        }
    }

    override fun initData() {
        setLiveDataStatus(viewModel.dataLiveData) {
            if (it != null && it) {
                getString(R.string.register_success_tip).showToast()
                ActivityCollector.remove(weakReference)
            } else getString(R.string.account_duplication_tip).showToast()
        }
    }

    /**
     * 验证用户注册参数是否合法函数
     * @param userAccount 用户账户
     * @param userPwd 用户一次输入密码
     * @param userTwoPwd 用户二次输入密码
     * @return 验证结果
     */
    private fun verificationRegisterParam(
        userAccount: String,
        userPwd: String,
        userTwoPwd: String
    ): Boolean {
        if (userAccount.isEmpty()) {
            getString(R.string.account_null_tip).showToast()
            return false
        }
        if (userAccount.length != 10) {
            getString(R.string.account_length_error_tip).showToast()
            return false
        }
        if (userPwd.isEmpty()) {
            getString(R.string.pwd_null_tip).showToast()
            return false
        }
        if (userPwd.length < 9) {
            getString(R.string.pwd_length_error_tip).showToast()
            return false
        }
        if (userTwoPwd.isEmpty()) {
            getString(R.string.two_pwd_null_tip).showToast()
            return false
        }
        if (userTwoPwd.length < 9) {
            getString(R.string.two_pwd_length_error_tip).showToast()
            return false
        }
        if (userPwd != userTwoPwd) {
            getString(R.string.two_different_password_tip).showToast()
            return false
        }
        return true
    }

    companion object {

        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, RegisterActivity::class.java)
            context.startActivity(starter)
        }
    }
}