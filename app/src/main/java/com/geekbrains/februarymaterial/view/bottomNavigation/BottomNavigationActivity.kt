package com.geekbrains.februarymaterial.view.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.geekbrains.februarymaterial.R
import com.geekbrains.februarymaterial.databinding.ActivityBottomNavigationBinding
import com.google.android.material.badge.BadgeDrawable.TOP_END


import com.google.android.material.badge.BadgeDrawable.TOP_START

class BottomNavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigationView()
    }

    private fun initBottomNavigationView() {
        badgeAdd()
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_view_earth -> {
                    navigationTo(EarthFragment())
                    true
                }
                R.id.bottom_view_mars -> {
                    navigationTo(MarsFragment())
                    true
                }
                R.id.bottom_view_system -> {
                    navigationTo(SystemFragment())
                    binding.bottomNavigationView.removeBadge(R.id.bottom_view_system)
                    true
                }
                else -> true
            }
        }
        binding.bottomNavigationView.selectedItemId = R.id.bottom_view_earth
    }

    private fun badgeAdd() {
        val badgeSystem = binding.bottomNavigationView.getOrCreateBadge(R.id.bottom_view_system)
        val badgeMars = binding.bottomNavigationView.getOrCreateBadge(R.id.bottom_view_mars)

        /*val badgeSystem*/
        badgeSystem.number = 3
        badgeSystem.maxCharacterCount = 2
        badgeSystem.badgeGravity = TOP_START
        /*val badgeMars*/
        badgeMars.number = 2
        badgeMars.maxCharacterCount = 2
        badgeMars.badgeGravity = TOP_END
    }

    private fun navigationTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

}