package com.geekbrains.februarymaterial.view


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.februarymaterial.R
import com.geekbrains.februarymaterial.databinding.MainActivityBinding
import com.geekbrains.februarymaterial.view.main.PictureOfTheDayFragment

const val ThemeOne = 1
const val ThemeTwo = 2
const val ThemeThree = 3

class MainActivity : AppCompatActivity() {

    private var _binding: MainActivityBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getRealStyle(getCurrentTheme())) // задаем тему

        setContentView(R.layout.main_activity) //сетим в R.id.container_main_activity - фрагмент
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_main_activity, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }

        /*Проверяем если интернет соединение*/
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkStateReceiver, filter)

    }

    /*Проверка если интернет соединение*/
    private var networkStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val noConnectivity =
                intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
            if (!noConnectivity) {
                onConnectionFound()
            } else {
                onConnectionLost()
            }
        }
    }
    fun onConnectionLost() {
        Toast.makeText(this, "Connection web no", Toast.LENGTH_LONG).show()
    }
    fun onConnectionFound() {
        Toast.makeText(this, "Connection web yes", Toast.LENGTH_LONG).show()
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

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkStateReceiver)
    }
}