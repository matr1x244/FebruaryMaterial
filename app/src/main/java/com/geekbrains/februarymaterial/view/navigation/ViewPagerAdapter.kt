package com.geekbrains.februarymaterial.view.navigation

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

const val EARTH_KEY = 0
const val MARS_KEY = 1
const val SYSTEM_KEY = 2

class ViewPagerAdapter(private val fragmentManager: FragmentActivity) : FragmentStateAdapter(fragmentManager) {

    private val fragments = arrayOf(EarthFragment(), MarsFragment(), SystemFragment())

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int)= fragments[position]

}