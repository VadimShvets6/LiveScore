package com.top1shvetsvadim1.footballivestream.presentation.fragment.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.top1shvetsvadim1.footballivestream.R
import com.top1shvetsvadim1.footballivestream.databinding.FragmentScreenOneBinding
import java.lang.RuntimeException


class ScreenOneFragment : Fragment() {

    private var _binding: FragmentScreenOneBinding? = null
    private val binding: FragmentScreenOneBinding
        get() = _binding ?: throw RuntimeException("FragmentScreenOneBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScreenOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonNext.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, ScreenTwoFragment.newInstance())
                .commit()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = ScreenOneFragment()
    }
}