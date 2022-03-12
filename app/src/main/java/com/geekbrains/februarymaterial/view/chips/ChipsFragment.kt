package com.geekbrains.februarymaterial.view.chips

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.geekbrains.februarymaterial.R
import com.geekbrains.februarymaterial.databinding.FragmentChipsBinding
import com.geekbrains.februarymaterial.view.MainActivity
import com.geekbrains.februarymaterial.view.ThemeOne
import com.geekbrains.februarymaterial.view.ThemeThree
import com.geekbrains.februarymaterial.view.ThemeTwo
import com.geekbrains.februarymaterial.view.layouts.LayoutActivity
import com.geekbrains.februarymaterial.view.navigation.BottomNavigationActivity
import com.geekbrains.februarymaterial.view.navigation.NavigationActivityTabs

class ChipsFragment : Fragment() {

    /*Для темы приложения*/
    private lateinit var parentActivity: MainActivity

    override fun onAttach(context: Context) { super.onAttach(context)
        parentActivity = activity as MainActivity
    }

    private var _binding: FragmentChipsBinding? = null
    private val binding: FragmentChipsBinding
        get() = _binding!!

    companion object {
        fun newInstance() = ChipsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChipsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabsClick()
        themeSet()
        themeGet()
        navigationClick()

    }

    /*RadioGroupClicks*/
    private fun themeGet() {
        when (parentActivity.getCurrentTheme()) {
            1 ->  binding.radioGroup.check(R.id.btnThemeOne)
            2 ->  binding.radioGroup.check(R.id.btnThemeTwo)
            3 ->  binding.radioGroup.check(R.id.btnThemeThree)
        }
    }

    private fun themeSet() {
        binding.btnThemeOne.setOnClickListener{
          binding.btnThemeOne.isChecked
            parentActivity.setCurrentTheme(ThemeOne)
            parentActivity.recreate()
        }
        binding.btnThemeTwo.setOnClickListener{
            binding.btnThemeTwo.isChecked
            parentActivity.setCurrentTheme(ThemeTwo)
            parentActivity.recreate()
        }
        binding.btnThemeThree.setOnClickListener{
            binding.btnThemeThree.isChecked
            parentActivity.setCurrentTheme(ThemeThree)
            parentActivity.recreate()
        }
        binding.switchNigth.setOnCheckedChangeListener { switchView, isChecked ->
            if(isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun navigationClick(){
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_one->{
                    startActivity(Intent(requireContext(), NavigationActivityTabs::class.java))
                }
                R.id.navigation_two->{
                    startActivity(Intent(requireContext(), BottomNavigationActivity::class.java))
                }
                R.id.navigation_three->{
                    startActivity(Intent(requireContext(), LayoutActivity::class.java))
                }
            }
            true
        }
    }



    /*TABS*/
    private fun tabsClick(){
        binding.tabs.getTabAt(0)?.text = "РАБОТАЕТ 1"
        binding.tabs.getTabAt(1)?.text = "РАБОТАЕТ 2"
        binding.tabs.getTabAt(2)?.text = "РАБОТАЕТ 3"
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}