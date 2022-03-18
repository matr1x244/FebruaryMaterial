package com.geekbrains.februarymaterial.view.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.geekbrains.februarymaterial.R
import com.geekbrains.februarymaterial.databinding.ActivityNavigationTabsBinding
import com.geekbrains.februarymaterial.view.ThemeOne
import com.geekbrains.februarymaterial.view.ThemeThree
import com.geekbrains.februarymaterial.view.ThemeTwo
import com.geekbrains.februarymaterial.view.navigation.viewpager.*
import com.google.android.material.tabs.TabLayoutMediator

class NavigationActivityTabs : AppCompatActivity() {

    lateinit var binding: ActivityNavigationTabsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getRealStyle(getCurrentTheme()))
        binding = ActivityNavigationTabsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewPagerCustom()
    }

    private fun ViewPagerCustom() {
        binding.viewPager.adapter = ViewPagerAdapter(this)
        binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        binding.viewPager.setPageTransformer(ZoomOutPageTransformer()) //переход стоковый

        TabLayoutMediator(binding.tabLayout,binding.viewPager
        ) { tab, position ->
            tab.text = when (position) {
                EARTH_KEY -> "Earth"
                MARS_KEY -> "Mars"
                SYSTEM_KEY -> "System"
                else -> "Earth"
            }
        }.attach()

        /*    binding.tabLayout.getTabAt(EARTH_KEY)?.setIcon(R.drawable.ic_earth)
            binding.tabLayout.getTabAt(MARS_KEY)?.setIcon(R.drawable.ic_mars)
            binding.tabLayout.getTabAt(SYSTEM_KEY)?.setIcon(R.drawable.ic_system)*/

        binding.tabLayout.getTabAt(EARTH_KEY)?.setCustomView(R.layout.activity_navigation_tablayout_item_earth)
        binding.tabLayout.getTabAt(MARS_KEY)?.setCustomView(R.layout.activity_navigation_tablayout_item_mars)
        binding.tabLayout.getTabAt(SYSTEM_KEY)?.setCustomView(R.layout.activity_navigation_tablayout_item_system)
    }

    /*Сетим тему*/
    fun getCurrentTheme(): Int {
        val sharedPreferences = getSharedPreferences("sp", MODE_PRIVATE)
        return sharedPreferences.getInt("current_theme", -1)
    }

    private fun getRealStyle(currentTheme: Int): Int {
        return when (currentTheme) {
            ThemeOne -> R.style.FebruaryMaterialBlue
            ThemeTwo -> R.style.FebruaryMaterialRed
            ThemeThree -> R.style.Theme_FebruaryMaterial
            else -> 0
        }
    }

}