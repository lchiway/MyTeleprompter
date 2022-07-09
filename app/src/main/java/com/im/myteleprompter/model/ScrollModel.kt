package com.im.myteleprompter.model

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.Delegates

/**
 *  Author : lchiway
 *  Date   : 2022/6/28
 *  Desc   : Model for scrolling function
 */
class ScrollModel(context: Context) {
    companion object{
        private lateinit var instance: ScrollModel
        fun getInstance(ctx: Context): ScrollModel {
            if (!this::instance.isInitialized) {
                instance = ScrollModel(ctx)
            }
            return instance
        }
    }

    private var start by Delegates.notNull<Int>()
    private var speed by Delegates.notNull<Long>()
    private var sp by Delegates.notNull<SharedPreferences>()

    init {
        sp = context.getSharedPreferences("user_settings", 0)
        start = sp.getInt("start", 0)
        speed = sp.getLong("speed", 50)
    }

    @JvmName("setStart1")
    fun setStart(p: Int){
        sp.edit().putInt("start", p).commit()
    }

    @JvmName("setSpeed1")
    fun setSpeed(p: Long){
        sp.edit().putLong("speed", p).commit()
    }

    @JvmName("getStart1")
    fun getStart(): Int{
        return sp.getInt("start", 0)
    }

    @JvmName("getSpeed1")
    fun getSpeed(): Long{
        return sp.getLong("speed", 50)
    }

    fun setFontSize(p: Float){
        sp.edit().putFloat("font_size", p).commit()
    }

    fun getFontSize(): Float{
        return sp.getFloat("font_size", 20.0F)
    }

    fun setIsScroll(p: Boolean){
        sp.edit().putBoolean("isScroll", p).commit()
    }

    fun getIsScroll(): Boolean{
        return sp.getBoolean("isScroll", false)
    }

    fun setIsMirror(p: Boolean){
        sp.edit().putBoolean("isMirror", p).commit()
    }

    fun getIsMirror(): Boolean{
        return sp.getBoolean("isMirror", false)
    }
}