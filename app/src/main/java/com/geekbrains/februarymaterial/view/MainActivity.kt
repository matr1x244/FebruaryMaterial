package com.geekbrains.februarymaterial.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geekbrains.februarymaterial.R
import com.geekbrains.februarymaterial.view.main.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        //сетим в R.id.container_main_activity - фрагмент
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_main_activity, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }
}