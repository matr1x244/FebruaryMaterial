package com.geekbrains.februarymaterial.view.layouts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.geekbrains.februarymaterial.R
import com.geekbrains.februarymaterial.databinding.ActivityLayoutBinding
import com.geekbrains.februarymaterial.view.ThemeOne
import com.geekbrains.februarymaterial.view.ThemeThree
import com.geekbrains.februarymaterial.view.ThemeTwo
import com.geekbrains.februarymaterial.view.layouts.constraint.ConstraintFragment
import com.geekbrains.februarymaterial.view.layouts.coordinator.CoordinatorFragment
import com.geekbrains.februarymaterial.view.layouts.motion.MotionFragment


class LayoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigationView()
    }

    private fun initBottomNavigationView() {

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_constraint -> {
                    navigationTo(ConstraintFragment())
                    true
                }
                R.id.bottom_coordinator -> {
                    navigationTo(CoordinatorFragment())
                    true
                }
                R.id.bottom_motion -> {
                    navigationTo(MotionFragment())
                    true
                }
                else -> true
            }
        }
        binding.bottomNavigationView.selectedItemId = R.id.bottom_constraint
    }

    private fun navigationTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

}