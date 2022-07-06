package com.example.mockingmerchantapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mockingmerchantapp.databinding.MainFragmentBinding
import com.example.mockingmerchantapp.ui.main.MainFragment
import com.example.mockingmerchantapp.ui.main.Payment_Method_Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }


    }

    override fun onBackPressed() {

    }
}