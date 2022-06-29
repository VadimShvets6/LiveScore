package com.top1shvetsvadim1.footballivestream.presentation.fragment.screens

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.top1shvetsvadim1.footballivestream.databinding.FragmentScreenThreeBinding
import com.top1shvetsvadim1.footballivestream.presentation.activity.RegisterActivity
import java.lang.RuntimeException


class ScreenThreeFragment : Fragment() {

    private var _binding: FragmentScreenThreeBinding? = null
    private val binding: FragmentScreenThreeBinding
        get() = _binding ?: throw RuntimeException("FragmentScreenThreeBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScreenThreeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonNext.setOnClickListener {
            Intent(requireContext(), RegisterActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = ScreenThreeFragment()
    }
}