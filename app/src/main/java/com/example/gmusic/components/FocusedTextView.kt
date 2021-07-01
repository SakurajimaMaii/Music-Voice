package com.example.gmusic.components

import android.content.Context
import android.util.AttributeSet

/**
 *作者:created by HP on 2021/7/1 18:25
 *邮箱:sakurajimamai2020@qq.com
 */
class FocusedTextView : androidx.appcompat.widget.AppCompatTextView {
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context) : super(context)

    //重写isFocused方法，让其一直返回true
    override fun isFocused(): Boolean {
        return true
    }
}