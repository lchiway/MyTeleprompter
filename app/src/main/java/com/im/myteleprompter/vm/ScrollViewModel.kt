package com.im.myteleprompter.vm

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.im.myteleprompter.model.ScrollModel

/**
 *  Author : lchiway
 *  Date   : 2022/6/28
 *  Desc   : ViewModel for scrolling function
 */
class ScrollViewModel(context: Context): ViewModel() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        private lateinit var instance: ScrollViewModel
        fun getInstance(ctx: Context): ScrollViewModel {
            if (!this::instance.isInitialized) {
                instance = ScrollViewModel(ctx)
            }
            return instance
        }
    }

    @SuppressLint("StaticFieldLeak")
    private var context: Context

    var mirrorData = MutableLiveData<Boolean>()
    var scrollData = MutableLiveData<Boolean>()
    var fontSizeData = MutableLiveData<Float>()
    var speedData = MutableLiveData<Long>()

    init {
        this.context = context
    }

    @JvmName("setStart1")
    fun setStart(p: Int){
        ScrollModel.getInstance(context).setStart(p)
    }

    @JvmName("setSpeed1")
    fun setSpeed(p: Long){
        speedData.value = p
        ScrollModel.getInstance(context).setSpeed(p)
    }

    @JvmName("getStart1")
    fun getStart(): Int{
        return ScrollModel.getInstance(context).getStart()
    }

    @JvmName("getSpeed1")
    fun getSpeed(): Long{
        return ScrollModel.getInstance(context).getSpeed()
    }

    fun setFontSize(p: Float){
        fontSizeData.value = p
        ScrollModel.getInstance(context).setFontSize(p)
    }

    fun getFontSize(): Float{
        return ScrollModel.getInstance(context).getFontSize()
    }

    fun setIsScroll(p: Boolean){
        scrollData.value = p
        ScrollModel.getInstance(context).setIsScroll(p)
    }

    fun getIsScroll(): Boolean{
        return ScrollModel.getInstance(context).getIsScroll()
    }

    fun setIsMirror(p: Boolean){
        mirrorData.value = p
        ScrollModel.getInstance(context).setIsMirror(p)
    }

    fun getIsMirror(): Boolean{
        return ScrollModel.getInstance(context).getIsMirror()
    }
}