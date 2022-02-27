package com.geekbrains.februarymaterial.view


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geekbrains.februarymaterial.R
import com.geekbrains.februarymaterial.view.main.PictureOfTheDayFragment

const val ThemeOne = 1
const val ThemeTwo = 2
const val ThemeThree = 3

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getRealStyle(getCurrentTheme())) // задаем тему
        setContentView(R.layout.main_activity) //сетим в R.id.container_main_activity - фрагмент
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_main_activity, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }

    /*Сетим и сохраняем тему*/
    fun setCurrentTheme(currentTheme: Int) {
        val sharedPreferences = getSharedPreferences("sp", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("current_theme", currentTheme)
        editor.apply()
    }

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