package cn.govast.gmusic.utils

import androidx.annotation.IntRange
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/29
// Description: 
// Documentation:
// Reference:

class ShaderDrawable(
    val shader: Shader,
    val width: Int,
    val height: Int
) : GradientDrawable() {
    private val paint = Paint()
    private var bound: RectF = RectF()

    init {
        paint.isAntiAlias = true
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRoundRect(bound, 0f, 0f, paint)
    }

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
        paint.shader = shader
        bound.set(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
    }

    override fun setAlpha(@IntRange(from = 0, to = 255) alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    /**
     * If the Android version is bigger than [Build.VERSION_CODES.Q],
     * the [getOpacity] will only return [PixelFormat.UNKNOWN]. And
     * this method is no longer used in graphics optimizations. Please
     * refer to [getOpacity](https://developer.android.com/reference/android/graphics/drawable/Drawable#getOpacity())
     *
     * @return
     */
    @Deprecated("Deprecated")
    override fun getOpacity(): Int {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            PixelFormat.UNKNOWN
        } else {
            if(paint.alpha < 255)
                PixelFormat.TRANSLUCENT
            else
                PixelFormat.OPAQUE
        }
    }

    override fun getIntrinsicHeight(): Int {
        return height
    }

    override fun getIntrinsicWidth(): Int {
        return width
    }
}