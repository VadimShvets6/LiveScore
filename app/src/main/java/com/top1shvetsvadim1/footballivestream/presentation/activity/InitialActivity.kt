package com.top1shvetsvadim1.footballivestream.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.top1shvetsvadim1.footballivestream.R
import com.top1shvetsvadim1.footballivestream.databinding.ActivityInitialBinding
import com.top1shvetsvadim1.footballivestream.presentation.fragment.screens.ScreenOneFragment

class InitialActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityInitialBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Log.d("InitialActivity", "start")
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ScreenOneFragment.newInstance())
            .commit()
    }
}