package com.geekbrains.februarymaterial.view.layouts.coordinator

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class NestedBehavior(context: Context,attr:AttributeSet): CoordinatorLayout.Behavior<View>(context,attr) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View)
    = dependency is AppBarLayout //связываем с appbar

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View)
    : Boolean {

        /*NestedScrollView - child в данном проекте*/
        val bar = dependency as AppBarLayout //связываем с appbar
        child.y = bar.height.toFloat()+bar.y

        return super.onDependentViewChanged(parent, child, dependency)
    }

}