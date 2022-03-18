package com.geekbrains.februarymaterial.view.navigation.viewpager

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.geekbrains.februarymaterial.view.navigation.EarthFragment
import com.geekbrains.februarymaterial.view.navigation.MarsFragment
import com.geekbrains.februarymaterial.view.navigation.SystemFragment

const val EARTH_KEY = 0
const val MARS_KEY = 1
const val SYSTEM_KEY = 2

class ViewPagerAdapter(private val fragmentManager: FragmentActivity) : FragmentStateAdapter(fragmentManager) {

    private val fragments = arrayOf(EarthFragment(), MarsFragment(), SystemFragment())

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int)= fragments[position]

}