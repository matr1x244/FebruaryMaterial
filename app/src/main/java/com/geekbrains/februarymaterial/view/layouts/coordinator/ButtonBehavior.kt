package com.geekbrains.februarymaterial.view.layouts.coordinator

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class ButtonBehavior (context: Context, attr:AttributeSet?=null): CoordinatorLayout.Behavior<View>(context,attr) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    )= dependency is AppBarLayout //связываем с appbar


    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {

        val bar = dependency as AppBarLayout //связываем с appbar
        val barHeight = bar.height.toFloat()
        val barY = bar.y

        /*Скрываем кнопку по условию*/
        /*NestedScrollView - child в данном проекте*/
        if(abs(barY)>(barHeight*2/3)){
            child.visibility = View.GONE
        }else{
            child.visibility = View.VISIBLE
            child.alpha = ((barHeight*2/3)-abs(barY/2))/(barHeight*2/3) //плавное исчезание
        }

        return super.onDependentViewChanged(parent, child, dependency)
    }

}