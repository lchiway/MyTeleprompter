package com.im.myteleprompter

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 *  Author : lchiway
 *  Date   : 2022/6/30
 *  Desc   :
 */
class TeleprompterApplication: Application() {
    companion object{
        const val TOKEN = "" //input your Token

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}