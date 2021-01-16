package com.example.android.cocktaildb.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.cocktaildb.R
import com.example.android.cocktaildb.ui.drinks.DrinksFragment


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.nav_host_fragment, DrinksFragment::class.java, null)
                .commit()
        }

    }
}