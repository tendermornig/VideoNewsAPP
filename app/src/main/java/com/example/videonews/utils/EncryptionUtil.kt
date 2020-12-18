package com.example.videonews.utils

import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * 使用MD5加密生成简单的加密密码
 *
 * @param strParam 用户密码
 * @return 生成好的加密密码
 */
fun encoderByMd5(strParam: String): String {
    var token = ""
    try {
        val md5 = MessageDigest.getInstance("MD5")
        token = BigInteger(1, md5.digest(strParam.toByteArray())).toString()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return token
}