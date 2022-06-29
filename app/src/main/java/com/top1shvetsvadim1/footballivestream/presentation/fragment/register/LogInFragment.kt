package com.top1shvetsvadim1.footballivestream.presentation.fragment.register

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.top1shvetsvadim1.footballivestream.R
import com.top1shvetsvadim1.footballivestream.databinding.FragmentLogInBinding
import com.top1shvetsvadim1.footballivestream.presentation.LogIn
import com.top1shvetsvadim1.footballivestream.presentation.Process
import com.top1shvetsvadim1.footballivestream.presentation.viewModel.RegisterViewModel
import com.top1shvetsvadim1.footballivestream.presentation.activity.InfoActivity
import java.lang.RuntimeException

class LogInFragment : Fragment() {

    private var _binding: FragmentLogInBinding? = null
    private val binding: FragmentLogInBinding
        get() = _binding ?: throw RuntimeException("FragmentLogInBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        viewModelObserves()
        myOnBackPressed()
    }

    private fun viewModelObserves() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is LogIn -> {
                    if (it.isSuccess) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.succesfull_login),
                            Toast.LENGTH_SHORT
                        ).show()
                        Intent(requireContext(), InfoActivity::class.java).apply {
                            startActivity(this)
                        }
                    } else if (!it.isSuccess) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.wrong_login_or_password),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                is Process -> {
                    if (it.isLoading) {
                        binding.progressBar.visibility = View.VISIBLE
                    } else if (!it.isLoading) {
                        binding.progressBar.visibility = View.GONE
                    }
                }
                else -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.email_already_registered),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        viewModel.errorInputEmail.observe(viewLifecycleOwner){
            viewModel.errorInputEmail.observe(viewLifecycleOwner) {
                val message = if (it) {
                    getString(R.string.invalid_email)
                } else {
                    null
                }
                binding.editTextEmail.error = message
            }
            viewModel.errorInputPassword.observe(viewLifecycleOwner) {
                val message = if (it) {
                    getString(R.string.invalid_password)
                } else {
                    null
                }
                binding.editTextPassword.error = message
            }
        }
    }

    private fun setListeners() {
        binding.tvHaveAnAccount.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_main_activity, SingUpFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }

        binding.tvLabelForgotPassword.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_main_activity, ResetPasswordFragment.newInstance())
                .commit()
        }

        binding.buttonLogIn.setOnClickListener {
            viewModel.validateInputLogin(
                binding.editTextEmail.text.toString().trim(),
                binding.editTextPassword.text.toString().trim()
            )
            if (binding.editTextEmail.text.toString().trim().isNotEmpty() &&
                binding.editTextPassword.text.toString().trim().isNotEmpty()
            ) {
                viewModel.logIn(
                    requireActivity(),
                    binding.editTextEmail.text.toString().trim(),
                    binding.editTextPassword.text.toString().trim()
                )
            }
        }
    }

    private fun myOnBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }

    companion object {

        @JvmStatic
        fun newInstance() = LogInFragment()
    }
}