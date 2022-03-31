package com.geekbrains.februarymaterial.view.ux

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.februarymaterial.R
import com.geekbrains.februarymaterial.view.MainActivity


class ThemeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FebruaryMaterial)
        setContentView(R.layout.activity_load_theme)

        /*Анимация загрузки*/
        findViewById<ImageView>(R.id.imageViewLoad).animate().rotationBy(720f).setDuration(2000L).start()
        ObjectAnimator.ofFloat(findViewById(R.id.imageViewLoad), View.ALPHA, 1.0f, 0.2f).setDuration(1000).start();

        /*Отложенный старт MainActivity*/
        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000L)
    }

}