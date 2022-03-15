package com.geekbrains.februarymaterial.utils

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/*CUSTOM VIEW по размерам*/

class EquilateralImageView @JvmOverloads constructor(
    context: Context,
    attributeSet:
    AttributeSet?=null,
    defStyle:Int = 0) : AppCompatImageView (context,attributeSet,defStyle) {

    /*метод по ширине и высоте сетим одинаково picture(view)*/
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}