package com.example.mockingmerchantapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.core.app.ActivityOptionsCompat

class Splash_screen : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            val bundle = ActivityOptionsCompat.makeCustomAnimation(applicationContext,
                android.R.anim.fade_in,
                android.R.anim.fade_out).toBundle()
            startActivity(intent,bundle)
            finish()
        }, 3000)
    }

}