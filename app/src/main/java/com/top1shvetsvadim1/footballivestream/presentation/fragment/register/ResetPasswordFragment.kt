package com.top1shvetsvadim1.footballivestream.presentation.fragment.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.top1shvetsvadim1.footballivestream.R
import com.top1shvetsvadim1.footballivestream.databinding.FragmentRegisterBinding
import com.top1shvetsvadim1.footballivestream.databinding.FragmentResetPasswordBinding
import com.top1shvetsvadim1.footballivestream.presentation.ResetPassword
import com.top1shvetsvadim1.footballivestream.presentation.viewModel.RegisterViewModel
import java.lang.RuntimeException

class ResetPasswordFragment : Fragment() {

    private var _binding: FragmentResetPasswordBinding? = null
    private val binding: FragmentResetPasswordBinding
        get() = _binding ?: throw RuntimeException("FragmentResetPasswordBinding == null")


    private val viewModel by lazy {
        ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickListeners()
        viewModelObserves()
        myOnBackPressed()
    }

    private fun onClickListeners() {
        binding.buttonBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container_main_activity,
                LogInFragment.newInstance()
            ).commit()
        }
        binding.buttonReset.setOnClickListener {
            viewModel.resetPassword(requireActivity(), binding.editTextEmail.text.toString().trim())
        }
    }

    override fun onPause() {
        super.onPause()
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun viewModelObserves() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is ResetPassword -> {
                    if (it.isReset) {
                        Toast.makeText(
                            requireContext(),
                            "Письмо для сброса пароля отправлено вам на почту",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (!it.isReset) {
                        Toast.makeText(
                            requireContext(),
                            "Возникла ошибка!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                else -> {
                    Toast.makeText(
                        requireContext(),
                        "Возникла ошибка!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun myOnBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                    requireActivity().supportFragmentManager.beginTransaction().replace(
                        R.id.fragment_container_main_activity,
                        LogInFragment.newInstance()
                    ).commit()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }

    companion object {

        @JvmStatic
        fun newInstance() = ResetPasswordFragment()
    }
}