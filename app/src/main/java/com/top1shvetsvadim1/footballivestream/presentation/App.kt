package com.top1shvetsvadim1.footballivestream.presentation

import android.app.Application
import android.content.Intent
import android.util.Log
import com.top1shvetsvadim1.footballivestream.presentation.activity.InitialActivity

const val APP_SETTINGS = "App settings"
const val IS_STARED_UP = "Is started up one"

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        val preferences = getSharedPreferences(APP_SETTINGS, MODE_PRIVATE)

        val flag = preferences.contains(IS_STARED_UP)
        Log.d("AppActiv",  flag.toString())
        if(!flag){
            Log.d("AppActiv",  flag.toString())
            val editor = preferences.edit()
            editor.putBoolean(IS_STARED_UP, true)
            editor.apply()
            Intent(this, InitialActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(this)
            }
        }
    }

}