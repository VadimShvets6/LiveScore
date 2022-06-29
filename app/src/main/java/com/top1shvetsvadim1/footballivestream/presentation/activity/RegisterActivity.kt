package com.top1shvetsvadim1.footballivestream.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.top1shvetsvadim1.footballivestream.R
import com.top1shvetsvadim1.footballivestream.databinding.ActivityMainBinding
import com.top1shvetsvadim1.footballivestream.presentation.fragment.register.LogInFragment
import com.top1shvetsvadim1.footballivestream.presentation.viewModel.RegisterViewModel
import com.top1shvetsvadim1.footballivestream.presentation.fragment.register.SingUpFragment

class RegisterActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy{
        ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.userHasEntered.observe(this){
            Log.d("CheckReg", it.toString())
            if(it){
                Intent(this, InfoActivity::class.java).apply {
                    startActivity(this)
                }
            } else{
                supportFragmentManager.popBackStack()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_main_activity, LogInFragment.newInstance())
                    .addToBackStack("SingUp")
                    .commit()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.registerCheck()
    }
}