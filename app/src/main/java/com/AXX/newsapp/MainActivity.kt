package com.AXX.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.AXX.newsapp.newsSources.newsSourcesFragment
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
   lateinit var viewBinding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,
                newsSourcesFragment(),
                )
            .commit()
    }
}