package com.top1shvetsvadim1.footballivestream.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import com.top1shvetsvadim1.footballivestream.R
import com.top1shvetsvadim1.footballivestream.databinding.ActivityInfoBinding
import com.top1shvetsvadim1.footballivestream.presentation.fragment.matchInfo.LiveScoreFragment
import com.top1shvetsvadim1.footballivestream.presentation.fragment.matchInfo.MatchMainFragment
import com.top1shvetsvadim1.footballivestream.presentation.fragment.profile.ProfileUserFragment
import com.top1shvetsvadim1.footballivestream.presentation.fragment.register.SingUpFragment
import com.top1shvetsvadim1.footballivestream.presentation.viewModel.RegisterViewModel

class InfoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityInfoBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        viewModel.registerCheck()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.userHasEntered.observe(this) {
            if (it) {
                binding.navView.setOnItemSelectedListener { item->
                    when (item.itemId) {
                        R.id.navigation_home -> {
                            supportFragmentManager.beginTransaction()
                                .replace(
                                    R.id.fragment_container_info_activity,
                                    MatchMainFragment.newInstance()
                                )
                                .addToBackStack("Match")
                                .commit()
                            true
                        }
                        R.id.navigation_dashboard -> {
                            supportFragmentManager.beginTransaction()
                                .replace(
                                    R.id.fragment_container_info_activity,
                                    LiveScoreFragment.newInstance()
                                )
                                .addToBackStack("LiveScore")
                                .commit()
                            true
                        }
                        R.id.navigation_profile->{
                            supportFragmentManager.beginTransaction()
                                .replace(
                                    R.id.fragment_container_info_activity,
                                    ProfileUserFragment.newInstance()
                                )
                                .addToBackStack("Profile")
                                .commit()
                            true
                        }
                        else -> {
                            false
                        }
                    }
                }
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragment_container_info_activity, MatchMainFragment.newInstance())
//                    .addToBackStack("Match")
//                    .commit()
            } else {
                Intent(this, RegisterActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
    }


}