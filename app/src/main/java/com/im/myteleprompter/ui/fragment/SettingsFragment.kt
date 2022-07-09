package com.im.myteleprompter.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatTextView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.checkbox.MaterialCheckBox
import com.im.myteleprompter.R
import com.im.myteleprompter.TeleprompterApplication
import com.im.myteleprompter.utils.ScrollUtils
import com.im.myteleprompter.vm.ScrollViewModel

@SuppressLint("NonConstantResourceId")
class SettingsFragment : Fragment() {

    @BindView(R.id.mcb_mirror)
    lateinit var mcbMirror: MaterialCheckBox

    @BindView(R.id.mcb_rolling)
    lateinit var mcbScrolling: MaterialCheckBox

    @BindView(R.id.actv_speed)
    lateinit var actvSpeed: AppCompatTextView

    @BindView(R.id.actv_font_size)
    lateinit var actvFontSize: AppCompatTextView

    @BindView(R.id.ib_font_size_add)
    lateinit var ibFontSizeAdd: ImageButton

    @BindView(R.id.ib_font_size_minus)
    lateinit var ibFontSizeMinus: ImageButton

    @BindView(R.id.ib_speed_add)
    lateinit var ibSpeedAdd: ImageButton

    @BindView(R.id.ib_speed_minus)
    lateinit var ibSpeedMinus: ImageButton

    private val myContext by lazy { TeleprompterApplication.context }
    private val scrollVMInstance by lazy { ScrollViewModel.getInstance(myContext) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val holder = inflater.inflate(R.layout.fragment_settings, container, false)
        ButterKnife.bind(this,holder)
        return holder
    }

    override fun onResume() {
        super.onResume()
        initDatas()
        initViews()
        setListener()
    }

    private fun initDatas(){
    }

    private fun initViews() {
        mcbMirror.isChecked = scrollVMInstance.getIsMirror()
        mcbScrolling.isChecked = scrollVMInstance.getIsScroll()
        actvSpeed.text = scrollVMInstance.getSpeed().toString()
        actvFontSize.text = scrollVMInstance.getFontSize().toInt().toString()
    }

    private fun setListener() {
        mcbMirror.setOnClickListener {
            scrollVMInstance.setIsMirror(mcbMirror.isChecked)
        }
        mcbScrolling.setOnClickListener {
            scrollVMInstance.setIsScroll(mcbScrolling.isChecked)
        }
        ibSpeedAdd.setOnClickListener {
            val speed = actvSpeed.text.toString().toLong() + 1
            actvSpeed.text = speed.toString()
            scrollVMInstance.setSpeed(speed)
        }
        ibSpeedMinus.setOnClickListener {
            val speed = actvSpeed.text.toString().toLong() - 1
            actvSpeed.text = (speed.toString())
            scrollVMInstance.setSpeed(speed)
        }
        ibFontSizeAdd.setOnClickListener {
            val speed = actvFontSize.text.toString().toFloat() + 1
            actvFontSize.text = (speed.toInt().toString())
            scrollVMInstance.setFontSize(speed)
        }
        ibFontSizeMinus.setOnClickListener {
            val speed = actvFontSize.text.toString().toFloat() - 1
            actvFontSize.text = (speed.toInt().toString())
            scrollVMInstance.setFontSize(speed)
        }
    }
}