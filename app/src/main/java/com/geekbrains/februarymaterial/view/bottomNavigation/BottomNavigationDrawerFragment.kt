package com.geekbrains.februarymaterial.view.bottomNavigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geekbrains.februarymaterial.R
import com.geekbrains.februarymaterial.databinding.BottomNavigationLayoutBinding
import com.geekbrains.februarymaterial.view.navigation.BottomNavigationActivity
import com.geekbrains.februarymaterial.view.navigation.NavigationActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/*BottomSheetDialogFragment*/

class BottomNavigationDrawerFragment: BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutBinding? = null
    private val binding: BottomNavigationLayoutBinding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_one->{
                    startActivity(Intent(requireContext(), NavigationActivity::class.java))
                }
                R.id.navigation_two->{
                    startActivity(Intent(requireContext(), BottomNavigationActivity::class.java))
                }
            }
            true
        }
    }
}