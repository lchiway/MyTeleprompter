package com.im.myteleprompter

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.widget.ImageButton
import androidx.core.view.GravityCompat
import androidx.core.widget.NestedScrollView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.textfield.TextInputEditText
import com.im.myteleprompter.utils.ScrollUtils
import com.im.myteleprompter.vm.ScrollViewModel

class MainActivity : AppCompatActivity() {

    @BindView(R.id.nsv_scroll)
    lateinit var nsvScroll: NestedScrollView

    @BindView(R.id.tiet_text)
    lateinit var tietText: TextInputEditText

    @BindView(R.id.listBtn)
    lateinit var listBtn: ImageButton

    @BindView(R.id.drawerLayout)
    lateinit var drawerLayout: DrawerLayout

    @BindView(R.id.ib_sp)
    lateinit var ibSP: ImageButton

    private var isStart = false
    private var start = 0
    private var isMirror = false
    private var isScroll = false
    private var fontSize = 20F
    private var speed: Long = 50
    private var offset = 0
    private val ScrollVMInstance by lazy { ScrollViewModel.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        setFullScreen()
    }

    override fun onResume() {
        super.onResume()
        initDatas()
        initViews()
        setListeners()
        startObserve()
    }

    private fun setFullScreen(){
        if (Build.VERSION.SDK_INT >= 30) {
            window.insetsController.also {
                it?.hide(WindowInsets.Type.statusBars())
                it?.hide(WindowInsets.Type.navigationBars())
                it?.hide(WindowInsets.Type.systemBars())
            }
        }
        else {
            val decorView = window.decorView
            window.statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    private fun initDatas(){
        start = ScrollVMInstance.getStart()
        isMirror = ScrollVMInstance.getIsMirror()
        ScrollUtils.getInstance().setMirror(isMirror)
        isScroll = ScrollVMInstance.getIsScroll()
        fontSize = ScrollVMInstance.getFontSize()
        speed = ScrollVMInstance.getSpeed()
    }

    private fun initViews() {
        setMirror(isMirror)
        tietText.textSize = fontSize
    }

    private fun setListeners(){
        listBtn.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        nsvScroll.setOnScrollChangeListener { _, _, scrollY, _ ,_ ->
            start = scrollY
            ScrollUtils.getInstance().setStart(start)
            ScrollViewModel.getInstance(this).setStart(start)
        }
        ibSP.setOnClickListener {
            isStart = when(!isStart && isScroll) {
                true -> {
                    ibSP.setBackgroundResource(R.drawable.ic_pause)
                    ScrollUtils.getInstance().scrollToBottom(nsvScroll, tietText)
                    true
                } else -> {
                    ibSP.setBackgroundResource(R.drawable.ic_start)
                    ScrollUtils.getInstance().stopScroll()
                    false
                }
            }
        }
    }

    private fun startObserve() {
        ScrollViewModel.getInstance(this).mirrorData.observe(this, Observer {
            setMirror(it)
            ScrollUtils.getInstance().setOffset(offset)
            ScrollUtils.getInstance().setStart(start)
            ScrollUtils.getInstance().setMirror(it)
            ScrollViewModel.getInstance(this).setStart(start)
            isMirror = it
        })
        ScrollViewModel.getInstance(this).scrollData.observe(this, Observer {
            ScrollUtils.getInstance().setStart(start)
            ScrollViewModel.getInstance(this).setStart(start)
            isScroll = it
        })
        ScrollViewModel.getInstance(this).speedData.observe(this, Observer {
            ScrollUtils.getInstance().setSpeed(it)
        })
        ScrollViewModel.getInstance(this).fontSizeData.observe(this, Observer {
            tietText.textSize = it
        })
    }

    private fun setMirror(ismirr: Boolean) {
        offset = tietText.measuredHeight - nsvScroll.measuredHeight
        Log.d("lzw", "setListener: ${offset - start}")
        //nsvScroll.scrollTo(0, offset - start)
        start = offset - start
        nsvScroll.scrollY = start

        when (ismirr) {
            true -> {
                tietText.scaleY = (-1).toFloat()
                offset = 0
            }
            else -> {
                tietText.scaleY = (1).toFloat()
            }
        }
    }
}