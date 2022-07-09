package com.im.myteleprompter.utils

import android.os.Handler
import android.os.Message
import android.view.View
import androidx.core.widget.NestedScrollView
import java.lang.Thread.sleep

/**
 *  Author : lchiway
 *  Date   : 2022/6/27
 *  Desc   :
 */
class ScrollUtils {
    companion object {
        private var instance: ScrollUtils = ScrollUtils()
        fun getInstance(): ScrollUtils {
            if (instance == null) {
                instance = ScrollUtils()
            }
            return instance
        }
    }

    private lateinit var handler: ScrollHandler
    private var start = 0
    private var offset = 0
    private var speed: Long = 20
    private var isStop = false
    private var step  = 1

    private lateinit var scrollThread: Thread

    fun scrollToBottom(sv: NestedScrollView, inner: View){
        handler = ScrollHandler(sv)
        if (sv == null || inner == null) {
            return
        }
        offset = inner.measuredHeight - sv.measuredHeight
        if (offset < 0) {
            offset = 0
        }
        isStop = false

        if(!this::scrollThread.isInitialized
            || scrollThread.state == Thread.State.TERMINATED)
            initThread()
        scrollThread.start()
    }

    fun stopScroll(){
        isStop = true
    }

    fun setSpeed(speed: Long){
        this.speed = 500/speed
    }

    fun setStart(start: Int){
        this.start = start
    }

    fun setOffset(offset: Int){
        this.offset = offset
    }

    fun setMirror(b: Boolean){
        step = when(b){
            true -> -1
            else -> 1
        }
    }

    private fun initThread(){
        scrollThread = Thread {
            while(start!=offset && !isStop) {
                var msg = Message()
                msg.what = 0
                msg.arg1 = start+step
                handler.sendMessage(msg)
                start += step
                sleep(speed)
            }
        }
    }

    class ScrollHandler(private var sv: NestedScrollView) : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                0 -> {
                    sv.scrollTo(0, msg.arg1)
                }
                1 -> {}
            }
        }
    }
}