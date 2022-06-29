package com.top1shvetsvadim1.footballivestream.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.top1shvetsvadim1.footballivestream.R
import com.top1shvetsvadim1.footballivestream.databinding.ActivityInfoBinding
import com.top1shvetsvadim1.footballivestream.presentation.fragment.matchInfo.MatchMainFragment
import com.top1shvetsvadim1.footballivestream.presentation.fragment.register.SingUpFragment
import com.top1shvetsvadim1.footballivestream.presentation.viewModel.RegisterViewModel

class InfoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityInfoBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy{
        ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        viewModel.registerCheck()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.userHasEntered.observe(this){
            if(it){
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_info_activity, MatchMainFragment.newInstance())
                    .addToBackStack("Match")
                    .commit()
            } else{
                Intent(this, RegisterActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
    }

}